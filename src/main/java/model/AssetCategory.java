package model;

public class AssetCategory {
    private String name;
    private String parentCategoryName;

    public AssetCategory() {}

    public AssetCategory(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(final String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
}
