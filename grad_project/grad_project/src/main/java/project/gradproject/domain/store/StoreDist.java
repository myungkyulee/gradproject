package project.gradproject.domain.store;


public class StoreDist implements Comparable<StoreDist>{
    Double dist;
    Store store;

    public Store getStore() {
        return this.store;
    }
    public void setStore(Store store){
        this.store = store;
    }
    public void setDist(Double dist){
        this.dist = dist;
    }
    public Double getDist() {
        return dist;
    }

    @Override
    public int compareTo(StoreDist o) {
        if(this.store.getStoreStatus()==o.store.getStoreStatus()){
            if(this.dist<o.dist){
                return 1;
            }
            return -1;
        }
        else {
            if(this.store.getStoreStatus()==StoreStatus.OPEN){
                return 1;
            }
            return -1;
        }

    }
}