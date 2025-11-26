package manager;

import exception.ValidationException;
import model.ExternalReferenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ExternalReferenceProviderManager {
    private final Map<UUID, ExternalReferenceProvider> byId = new ConcurrentHashMap<>();
    private final Map<String, ExternalReferenceProvider> byName = new ConcurrentHashMap<>();

    public ExternalReferenceProvider registerProvider(final ExternalReferenceProvider provider) {
        if (provider == null) throw new ValidationException("provider cannot be null");
        if (provider.getName() == null || provider.getName().isBlank()) throw new ValidationException("provider name required");
        if (byName.containsKey(provider.getName())) {
            throw new ValidationException("provider with name already exists: " + provider.getName());
        }
        UUID id = provider.getId() == null ? UUID.randomUUID() : provider.getId();
        provider.setId(id);
        byId.put(id, provider);
        byName.put(provider.getName(), provider);
        return provider;
    }

    public Optional<ExternalReferenceProvider> findByName(String name) {
        return Optional.ofNullable(byName.get(name));
    }

    public Optional<ExternalReferenceProvider> findById(UUID id) {
        return Optional.ofNullable(byId.get(id));
    }

    public List<ExternalReferenceProvider> listAll() {
        return new ArrayList<>(byId.values());
    }
}
