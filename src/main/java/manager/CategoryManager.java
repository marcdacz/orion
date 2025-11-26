package manager;

import exception.ValidationException;
import model.AssetCategory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CategoryManager {
    private final Map<UUID, AssetCategory> categories = new ConcurrentHashMap<>();

    public AssetCategory createCategory(String name, UUID parentId) {
        if (name == null || name.isBlank()) throw new ValidationException("category name required");
        if (parentId != null && !categories.containsKey(parentId)) {
            throw new ValidationException("parent category not found: " + parentId);
        }

        // Prevent duplicate names under the same parent
        boolean duplicate = categories.values().stream()
                .anyMatch(c -> Objects.equals(c.getParentId(), parentId) && c.getName().equalsIgnoreCase(name));
        if (duplicate) throw new ValidationException("category with same name already exists under parent");

        AssetCategory category = new AssetCategory(UUID.randomUUID(), name, parentId);

        // Prevent cycle: ensure parent chain does not include this id (it won't because new id not yet present),
        // but check parent chain for cycle if parent was set later (it won't happen here), safe anyway.
        categories.put(category.getId(), category);
        return category;
    }

    public AssetCategory getCategory(UUID id) {
        return categories.get(id);
    }

    public List<AssetCategory> listChildren(UUID parentId) {
        return categories.values().stream()
                .filter(c -> Objects.equals(c.getParentId(), parentId))
                .sorted(Comparator.comparing(AssetCategory::getName))
                .collect(Collectors.toList());
    }

    public List<AssetCategory> getAncestors(UUID id) {
        List<AssetCategory> ancestors = new ArrayList<>();
        AssetCategory current = categories.get(id);
        while (current != null && current.getParentId() != null) {
            AssetCategory parent = categories.get(current.getParentId());
            if (parent == null) break;
            // detect cycle defensively
            if (ancestors.stream().anyMatch(a -> a.getId().equals(parent.getId()))) {
                throw new ValidationException("cycle detected in category ancestors for id: " + id);
            }
            ancestors.add(parent);
            current = parent;
        }
        return ancestors;
    }

    public List<AssetCategory> getDescendants(UUID id) {
        List<AssetCategory> result = new ArrayList<>();
        Deque<UUID> stack = new ArrayDeque<>();
        stack.push(id);
        while (!stack.isEmpty()) {
            UUID currentId = stack.pop();
            for (AssetCategory child : listChildren(currentId)) {
                result.add(child);
                stack.push(child.getId());
            }
        }
        return result;
    }

    public boolean wouldCreateCycle(UUID candidateId, UUID newParentId) {
        if (candidateId == null || newParentId == null) return false;
        UUID current = newParentId;
        while (current != null) {
            if (current.equals(candidateId)) return true;
            AssetCategory parent = categories.get(current);
            if (parent == null) break;
            current = parent.getParentId();
        }
        return false;
    }
}
