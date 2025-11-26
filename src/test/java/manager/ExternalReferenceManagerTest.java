package manager;

import exception.ValidationException;
import model.ExternalReference;
import model.ExternalReferenceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExternalReferenceManagerTest {
    private ExternalReferenceManager externalReferenceManager;
    private ExternalReferenceProviderManager providerManager;

    @BeforeEach
    void setUp() {
        providerManager = mock(ExternalReferenceProviderManager.class);
        externalReferenceManager = new ExternalReferenceManager(providerManager);
    }

    @Test
    void createReference__throws_validation_exception__when_assetId_is_null() {
        assertThrows(ValidationException.class, () ->
                externalReferenceManager.createReference(null, UUID.randomUUID(), UUID.randomUUID(), "https://example.com", null));
    }

    @Test
    void createReference__throws_validation_exception__when_providerId_is_null() {
        assertThrows(ValidationException.class, () ->
                externalReferenceManager.createReference(UUID.randomUUID(), null, UUID.randomUUID(), "https://example.com", null));
    }

    @Test
    void createReference__throws_validation_exception__when_referenceId_is_null() {
        assertThrows(ValidationException.class, () ->
                externalReferenceManager.createReference(UUID.randomUUID(), UUID.randomUUID(), null, "https://example.com", null));
    }

    @Test
    void createReference__throws_validation_exception__when_provider_not_found() {
        UUID providerId = UUID.randomUUID();
        when(providerManager.findById(providerId)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () ->
                externalReferenceManager.createReference(UUID.randomUUID(), providerId, UUID.randomUUID(), "https://example.com", null));
    }

    @Test
    void createReference__throws_validation_exception__when_url_is_invalid() {
        UUID providerId = UUID.randomUUID();
        when(providerManager.findById(providerId)).thenReturn(Optional.of(new ExternalReferenceProvider()));

        assertThrows(ValidationException.class, () ->
                externalReferenceManager.createReference(UUID.randomUUID(), providerId, UUID.randomUUID(), "ht!tp://badurl", null));
    }

    @Test
    void createResource__creates_reference_successfully() {
        UUID assetId = UUID.randomUUID();
        UUID providerId = UUID.randomUUID();
        UUID referenceId = UUID.randomUUID();
        String url = "https://example.com";

        ExternalReferenceProvider provider = new ExternalReferenceProvider();
        when(providerManager.findById(providerId)).thenReturn(Optional.of(provider));

        ExternalReference resource = externalReferenceManager.createReference(assetId, providerId, referenceId, url, Map.of("key", "value"));

        assertNotNull(resource);
        assertEquals(assetId, resource.getAssetId());
        assertEquals(providerId, resource.getProviderId());
        assertEquals(referenceId, resource.getReferenceId());
        assertEquals(url, resource.getUrl());
        assertNotNull(resource.getCreatedAt());
        assertNotNull(resource.getUpdatedAt());
    }

    @Test
    void listByAsset__returns_empty_list__when_no_resources_exist() {
        UUID assetId = UUID.randomUUID();
        List<ExternalReference> resources = externalReferenceManager.listByAsset(assetId);

        assertNotNull(resources);
        assertTrue(resources.isEmpty());
    }

    @Test
    void listByAsset__returns_resources_for_asset() {
        UUID assetId = UUID.randomUUID();
        UUID providerId = UUID.randomUUID();
        UUID referenceId = UUID.randomUUID();

        ExternalReferenceProvider provider = new ExternalReferenceProvider();
        when(providerManager.findById(providerId)).thenReturn(Optional.of(provider));

        externalReferenceManager.createReference(assetId, providerId, referenceId, "https://example.com", null);

        List<ExternalReference> resources = externalReferenceManager.listByAsset(assetId);

        assertEquals(1, resources.size());
        assertEquals(assetId, resources.get(0).getAssetId());
    }
}