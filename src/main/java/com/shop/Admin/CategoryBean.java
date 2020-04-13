package com.shop.Admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.shop.Admin.DAO.CategoryDAO;
import com.shop.models.Category;


@ManagedBean(name="categoryBean")
@SessionScoped
public class CategoryBean implements Serializable {
	private CategoryDAO categoryService = new CategoryDAO();
	private Category new_category = new Category();
	private Category current_category;
	private List<Category> listCategory;
	public CategoryDAO getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryDAO categoryService) {
		this.categoryService = categoryService;
	}
	public Category getNew_category() {
		return new_category;
	}
	public void setNew_category(Category new_category) {
		this.new_category = new_category;
	}
	public Category getCurrent_category() {
		return current_category;
	}
	public void setCurrent_category(Category current_category) {
		this.current_category = current_category;
	}
	public List<Category> getListCategory() {
		return listCategory;
	}
	public void setListCategory(List<Category> listCategory) {
		this.listCategory = listCategory;
	}
	public List<Category> getCategories()
	{
		return categoryService.getListCategory();
	}
	public Map<String, Integer> getDropdownCategory(){
		return categoryService.getDropdownCategory();
	}
}
