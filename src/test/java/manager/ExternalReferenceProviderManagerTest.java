package manager;

import exception.ValidationException;
import model.ExternalReferenceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExternalReferenceProviderManagerTest {
    private ExternalReferenceProviderManager providerManager;

    @BeforeEach
    void setUp() {
        providerManager = new ExternalReferenceProviderManager();
    }

    @Test
    void registerProvider__throws_validation_exception__when_provider_is_null() {
        assertThrows(ValidationException.class, () -> providerManager.registerProvider(null));
    }

    @Test
    void registerProvider__throws_validation_exception__when_provider_name_is_null_or_blank() {
        ExternalReferenceProvider provider = new ExternalReferenceProvider();
        provider.setName(null);
        assertThrows(ValidationException.class, () -> providerManager.registerProvider(provider));

        provider.setName("");
        assertThrows(ValidationException.class, () -> providerManager.registerProvider(provider));
    }

    @Test
    void registerProvider__throws_validation_exception__when_provider_name_already_exists() {
        ExternalReferenceProvider provider1 = new ExternalReferenceProvider();
        provider1.setName("Provider1");
        providerManager.registerProvider(provider1);

        ExternalReferenceProvider provider2 = new ExternalReferenceProvider();
        provider2.setName("Provider1");
        assertThrows(ValidationException.class, () -> providerManager.registerProvider(provider2));
    }

    @Test
    void registerProvider__registers_provider_successfully() {
        ExternalReferenceProvider provider = new ExternalReferenceProvider();
        provider.setName("Provider1");

        ExternalReferenceProvider registeredProvider = providerManager.registerProvider(provider);

        assertNotNull(registeredProvider.getId());
        assertEquals("Provider1", registeredProvider.getName());
    }

    @Test
    void findByName__returns_provider_when_name_exists() {
        ExternalReferenceProvider provider = new ExternalReferenceProvider();
        provider.setName("Provider1");
        providerManager.registerProvider(provider);

        Optional<ExternalReferenceProvider> foundProvider = providerManager.findByName("Provider1");

        assertTrue(foundProvider.isPresent());
        assertEquals("Provider1", foundProvider.get().getName());
    }

    @Test
    void findByName__returns_empty_optional_when_name_does_not_exist() {
        Optional<ExternalReferenceProvider> foundProvider = providerManager.findByName("NonExistent");

        assertFalse(foundProvider.isPresent());
    }

    @Test
    void findById__returns_provider_when_id_exists() {
        ExternalReferenceProvider provider = new ExternalReferenceProvider();
        provider.setName("Provider1");
        ExternalReferenceProvider registeredProvider = providerManager.registerProvider(provider);

        Optional<ExternalReferenceProvider> foundProvider = providerManager.findById(registeredProvider.getId());

        assertTrue(foundProvider.isPresent());
        assertEquals(registeredProvider.getId(), foundProvider.get().getId());
    }

    @Test
    void findById__returns_empty_optional_when_id_does_not_exist() {
        Optional<ExternalReferenceProvider> foundProvider = providerManager.findById(UUID.randomUUID());

        assertFalse(foundProvider.isPresent());
    }

    @Test
    void listAll__returns_all_registered_providers() {
        ExternalReferenceProvider provider1 = new ExternalReferenceProvider();
        provider1.setName("Provider1");
        providerManager.registerProvider(provider1);

        ExternalReferenceProvider provider2 = new ExternalReferenceProvider();
        provider2.setName("Provider2");
        providerManager.registerProvider(provider2);

        List<ExternalReferenceProvider> providers = providerManager.listAll();

        assertEquals(2, providers.size());
    }
}