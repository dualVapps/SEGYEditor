package vladimir.seis.segystream;


import org.jfree.data.category.DefaultCategoryDataset;

public class DefaultCategoryDatasetRewrite extends DefaultCategoryDataset {

    int numberDataset;

    public DefaultCategoryDatasetRewrite(int numberDataset) {
        super();
        this.numberDataset = numberDataset;
    }

    public int getNumberDataset() {
        return numberDataset;
    }
}
