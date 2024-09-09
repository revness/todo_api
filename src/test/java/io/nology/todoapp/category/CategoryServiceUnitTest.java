package io.nology.todoapp.category;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.nology.todoapp.common.ValidationErrors;
import io.nology.todoapp.common.exceptions.ServiceValidationException;

public class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findById() {
        Long categoryId = 1L;
        categoryService.findById(categoryId);
        verify(repo).findById(categoryId);
    }

    @Test
    public void getAllCategories() {
        categoryService.findAll();
        verify(repo).findAll();
    }

    @Test
    public void createCategory_success() throws ServiceValidationException {
        CreateCategoryDTO mockDTO = new CreateCategoryDTO();
        mockDTO.setName("testing");

        Category mockCategory = new Category();
        mockCategory.setName(mockDTO.getName());

        when(repo.existsByName(mockDTO.getName())).thenReturn(false);
        when(repo.save(any(Category.class))).thenReturn(mockCategory);

        Category result = categoryService.create(mockDTO);

        assertNotNull(result);
        assertEquals(mockCategory.getName(), result.getName());

        verify(repo).existsByName(mockDTO.getName());
        verify(repo).save(any(Category.class));
    }

    @Test
    public void createCategory_exception() {
        CreateCategoryDTO mockDTO = new CreateCategoryDTO();
        mockDTO.setName("testing");

        ValidationErrors expectedErrors = new ValidationErrors();
        expectedErrors.addError("name", "category with name 'testing' already exists");

        when(repo.existsByName(mockDTO.getName())).thenReturn(true);

        try {
            categoryService.create(mockDTO);
            fail("ServiceValidationException not thrown");
        } catch (ServiceValidationException e) {
            ValidationErrors actualErrors = e.getErrors();
            assertEquals(expectedErrors.getErrors(), actualErrors.getErrors());
        }

        verify(repo).existsByName(mockDTO.getName());
    }

    @Test
    public void deleteById_success() {
        Category category = new Category();
        category.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.deleteById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    public void deleteById_notFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        Optional<Category> result = categoryService.deleteById(1L);
        assertFalse(result.isPresent());
        verify(repo, never()).deleteById(1L);
    }
}