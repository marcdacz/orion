package util;

import model.Asset;
import model.AssetCategory;
import model.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class InMemoryData {
    private List<Asset> assets = new ArrayList<>();
    private Map<String, AssetCategory> categories = new TreeMap<>();
    private Set<Tag> tags = new HashSet<>();

    public InMemoryData() {}

    public InMemoryData(final List<Asset> assets,
                        final Map<String, AssetCategory> categories,
                        final Set<Tag> tags) {
        this.assets = assets;
        this.categories = categories;
        this.tags = tags;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(final List<Asset> assets) {
        this.assets = assets;
    }

    public Map<String, AssetCategory> getCategories() {
        return categories;
    }

    public void setCategories(final Map<String, AssetCategory> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }
}
