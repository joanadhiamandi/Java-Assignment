/**
 * This class models a quiz category.
 * It encapsulates data related to a specific category,
 * including its unique identifier (category_id),
 * human-readable name (category_name), 
 * and the API URL (api_url) used to fetch questions related to that category.
 */

public class QuestionCategories {
    private int categoryId;
    private String category;
    private String api_url;

    /**
     * Initializes a new QuestionCategories object with the following information:
     * categoryId: The unique identifier for the category.
     * category: The human-readable name of the category.
     * api_url: The API URL used to retrieve questions related to this category.
     */
    public QuestionCategories(int categoryId, String category, String api_url) {
        this.categoryId = categoryId;
        this.category = category;
        this.api_url = api_url;
    }

    /**
     * Retrieves the human-readable name of this category.
     *
     * @return The category name.
     */
    public String getCategoryName() {
        return category;
    }

    /**
     * Retrieves the unique identifier for this category.
     *
     * @return The category ID.
     */
    public int getCategoryId() {
        return categoryId;
    }

 
    public String getApi_url() {
        return api_url;
    }
}
