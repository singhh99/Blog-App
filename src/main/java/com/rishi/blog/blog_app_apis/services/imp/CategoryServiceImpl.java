package com.rishi.blog.blog_app_apis.services.imp;

import com.rishi.blog.blog_app_apis.entities.Category;
import com.rishi.blog.blog_app_apis.exceptions.ResourceNotFoundException;
import com.rishi.blog.blog_app_apis.payloads.CategoryDto;
import com.rishi.blog.blog_app_apis.repositories.CategoryRepo;
import com.rishi.blog.blog_app_apis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       Category cat= this.modelMapper.map(categoryDto, Category.class);
       Category addedCat= this.categoryRepo.save(cat);
       return  this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory=this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        this.categoryRepo.delete(cat);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        return this.modelMapper.map(cat, CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getCategories() {
       List<Category> categories= this.categoryRepo.findAll();
        List<CategoryDto> catDto=categories.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return catDto;
    }
}
