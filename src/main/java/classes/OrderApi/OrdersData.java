package classes.OrderApi;

import java.util.List;

public class OrdersData {
    private List<Order> items;
    private int page;
    private int pageSize;
    private int total;
    private int pages;

    // No-args constructor
    public OrdersData() {
    }

    // All-args constructor
    public OrdersData(List<Order> items, int page, int pageSize, int total, int pages) {
        this.items = items;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
    }

    public List<Order> getItems() {
        return items;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "OrdersData{" +
                "items=" + items +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }
}
