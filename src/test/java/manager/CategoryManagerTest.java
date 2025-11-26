package manager;

import exception.ValidationException;
import model.AssetCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CategoryManagerTest {
    private CategoryManager categoryManager;

    @BeforeEach
    void setUp() {
        categoryManager = new CategoryManager();
    }

    @Test
    void createCategory__throws_validation_exception__when_name_is_null() {
        assertThrows(ValidationException.class, () -> categoryManager.createCategory(null, null));
    }

    @Test
    void createCategory__throws_validation_exception__when_name_is_blank() {
        assertThrows(ValidationException.class, () -> categoryManager.createCategory("   ", null));
    }

    @Test
    void createCategory__throws_validation_exception__when_parent_not_found() {
        UUID parentId = UUID.randomUUID();
        assertThrows(ValidationException.class, () -> categoryManager.createCategory("Child", parentId));
    }

    @Test
    void createCategory__throws_validation_exception__when_duplicate_name_under_same_parent() {
        AssetCategory parent = categoryManager.createCategory("Parent", null);
        categoryManager.createCategory("Child", parent.getId());
        assertThrows(ValidationException.class, () -> categoryManager.createCategory("Child", parent.getId()));
    }

    @Test
    void createCategory__creates_category_successfully() {
        AssetCategory category = categoryManager.createCategory("Category", null);
        assertNotNull(category);
        assertEquals("Category", category.getName());
        assertNull(category.getParentId());
    }

    @Test
    void getCategory__returns_correct_category() {
        AssetCategory category = categoryManager.createCategory("Category", null);
        AssetCategory fetched = categoryManager.getCategory(category.getId());
        assertEquals(category, fetched);
    }

    @Test
    void listChildren__returns_sorted_children() {
        AssetCategory parent = categoryManager.createCategory("Parent", null);
        categoryManager.createCategory("ChildB", parent.getId());
        categoryManager.createCategory("ChildA", parent.getId());

        List<AssetCategory> children = categoryManager.listChildren(parent.getId());
        assertEquals(2, children.size());
        assertEquals("ChildA", children.get(0).getName());
        assertEquals("ChildB", children.get(1).getName());
    }

    @Test
    void getAncestors__returns_correct_ancestors() {
        AssetCategory grandparent = categoryManager.createCategory("Grandparent", null);
        AssetCategory parent = categoryManager.createCategory("Parent", grandparent.getId());
        AssetCategory child = categoryManager.createCategory("Child", parent.getId());

        List<AssetCategory> ancestors = categoryManager.getAncestors(child.getId());
        assertEquals(2, ancestors.size());
        assertEquals("Parent", ancestors.get(0).getName());
        assertEquals("Grandparent", ancestors.get(1).getName());
    }

    @Test
    void getAncestors__throws_validation_exception__when_cycle_detected() {
        AssetCategory category = categoryManager.createCategory("Category", null);
        categoryManager.createCategory("Child", category.getId());
        categoryManager.getCategory(category.getId()).setParentId(category.getId()); // Simulate cycle

        assertThrows(ValidationException.class, () -> categoryManager.getAncestors(category.getId()));
    }

    @Test
    void getDescendants__returns_correct_descendants() {
        AssetCategory parent = categoryManager.createCategory("Parent", null);
        AssetCategory child1 = categoryManager.createCategory("Child1", parent.getId());
        AssetCategory child2 = categoryManager.createCategory("Child2", parent.getId());
        categoryManager.createCategory("Grandchild", child1.getId());

        List<AssetCategory> descendants = categoryManager.getDescendants(parent.getId());
        assertEquals(3, descendants.size());
        assertTrue(descendants.contains(child1));
        assertTrue(descendants.contains(child2));
    }

    @Test
    void wouldCreateCycle__returns_true__when_cycle_detected() {
        AssetCategory parent = categoryManager.createCategory("Parent", null);
        AssetCategory child = categoryManager.createCategory("Child", parent.getId());

        assertTrue(categoryManager.wouldCreateCycle(parent.getId(), child.getId()));
    }

    @Test
    void wouldCreateCycle__returns_false__when_no_cycle_detected() {
        AssetCategory parent = categoryManager.createCategory("Parent", null);
        AssetCategory child = categoryManager.createCategory("Child", parent.getId());

        assertFalse(categoryManager.wouldCreateCycle(child.getId(), parent.getId()));
    }

}