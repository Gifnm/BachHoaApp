package ioc.app.bachhoa.model;
import java.util.List;

public class Categories {

    private int categoriesID;

    private String categoriesName;

	public Categories(int categoriesID, String categoriesName) {
		this.categoriesID = categoriesID;
		this.categoriesName = categoriesName;
	}

	public Categories(int categoriesID) {
		this.categoriesID = categoriesID;
	}

	public Categories() {
	}

	public int getCategoriesID() {
		return categoriesID;
	}

	public void setCategoriesID(int categoriesID) {
		this.categoriesID = categoriesID;
	}

	public String getCategoriesName() {
		return categoriesName;
	}

	public void setCategoriesName(String categoriesName) {
		this.categoriesName = categoriesName;
	}


    
}
