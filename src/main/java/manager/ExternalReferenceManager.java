package manager;

import exception.ValidationException;
import model.ExternalReference;
import model.ExternalReferenceProvider;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ExternalReferenceManager {
    private final ExternalReferenceProviderManager providerManager;
    private final Map<UUID, ExternalReference> referencedById = new ConcurrentHashMap<>();
    private final Map<UUID, List<ExternalReference>> resourcesByAsset = new ConcurrentHashMap<>();

    public ExternalReferenceManager(final ExternalReferenceProviderManager providerManager) {
        this.providerManager = providerManager;
    }

    public ExternalReference createReference(UUID assetId, UUID providerId, UUID referenceId, String url, Map<String, Object> metadata) {
        if (assetId == null) throw new ValidationException("assetId required");
        if (providerId == null) throw new ValidationException("providerId required");
        if (referenceId == null) throw new ValidationException("referenceId required");

        ExternalReferenceProvider provider = providerManager.findById(providerId)
                .orElseThrow(() -> new ValidationException("provider not found: " + providerId));

        if ((url == null || url.isBlank()) && (provider.getBaseUrl() == null || provider.getBaseUrl().isBlank())) {
            throw new ValidationException("either a valid URL or provider base URL is required");
        }

        if (url != null && !url.isBlank()) {
            validateUrl(url);
        }

        ExternalReference r = new ExternalReference();
        r.setId(UUID.randomUUID());
        r.setAssetId(assetId);
        r.setProviderId(providerId);
        r.setReferenceId(referenceId);
        r.setUrl(url);
        r.setMetadata(metadata);
        r.setCreatedAt(LocalDateTime.now());
        r.setUpdatedAt(LocalDateTime.now());

        referencedById.put(r.getId(), r);
        resourcesByAsset.computeIfAbsent(assetId, k -> new ArrayList<>()).add(r);
        return r;
    }

    private void validateUrl(String url) {
        try {
            new URI(url);
        } catch (URISyntaxException ex) {
            throw new ValidationException("invalid url: " + url);
        }
    }

    public List<ExternalReference> listByAsset(UUID assetId) {
        return Collections.unmodifiableList(resourcesByAsset.getOrDefault(assetId, Collections.emptyList()));
    }
}
