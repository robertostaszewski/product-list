package com.product.productlist.entity;

public class ProductList {
    private final ProductListId productListId;
    private final Username owner;
    private final String name;

    private ProductList(ProductListId productListId, Username owner, String name) {
        this.productListId = productListId;
        this.owner = owner;
        this.name = name;
    }

    public ProductListId getProductListId() {
        return productListId;
    }

    public String getName() {
        return name;
    }

    public Username getOwner() {
        return owner;
    }

    public Product addProduct(String productName) {
        return Product.of(productListId, productName);
    }

    public static ProductList of(String name, Username owner) {
        return new ProductList(ProductListId.random(), owner, name);
    }

    public SharingEntry shareWith(Username username) {
        return new SharingEntry(SharingEntryId.random(), productListId, username);
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "productListId=" + productListId +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductList that = (ProductList) o;

        if (productListId != null ? !productListId.equals(that.productListId) : that.productListId != null)
            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = productListId != null ? productListId.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
