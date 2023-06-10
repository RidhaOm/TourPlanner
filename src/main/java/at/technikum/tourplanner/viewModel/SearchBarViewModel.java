package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.service.SearchService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchBarViewModel {

    private final SearchService searchService;
    private final StringProperty searchTextField = new SimpleStringProperty();

    public SearchBarViewModel(SearchService searchService) {
        this.searchService = searchService;
    }

    public void search() {
        String textToSearch = getSearchTextField();
        searchService.search(textToSearch);
    }

    // Getter for searchText property
    public String getSearchTextField() {
        return searchTextField.get();
    }

    // Setter for searchText property
    public void setSearchTextField(String searchTextField) {
        this.searchTextField.set(searchTextField);
    }

    // Getter for searchText property as StringProperty
    public StringProperty searchTextFieldProperty() {
        return searchTextField;
    }


}
