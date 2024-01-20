package mk.ukim.finki.wp.exam.example.web;

import mk.ukim.finki.wp.exam.example.model.Category;
import mk.ukim.finki.wp.exam.example.model.Product;
import mk.ukim.finki.wp.exam.example.service.CategoryService;
import mk.ukim.finki.wp.exam.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductsController {

    private final ProductService service;
    private final CategoryService categoryService;
    public ProductsController(ProductService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }


    @GetMapping({"/","/products"})
    public String showProducts(
            @RequestParam(required = false) String nameSearch,
            @RequestParam(required = false) Long categoryId,
            Model model) {
        List<Category> categories = categoryService.listAll();
        List<Product> products = this.service.listProductsByNameAndCategory(nameSearch, categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories",categories);
        return "list";
    }

    @GetMapping("/products/add")
    public String showAdd(Model model) {
        List<Category> categories = categoryService.listAll();
        model.addAttribute("categories",categories);
        return "form";
    }

    @GetMapping("/products/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        List<Category> categories = categoryService.listAll();
        model.addAttribute("categories",categories);
        model.addAttribute("product",this.service.findById(id));
        return "form";
    }


    public String create(String name, Double price, Integer quantity, List<Long> categories) {
        this.service.create(name, price, quantity, categories);
        return "";
    }

    public String update(Long id, String name, Double price, Integer quantity, List<Long> categories) {
        this.service.update(id, name, price, quantity, categories);
        return "";
    }

    public String delete(Long id) {
        this.service.delete(id);
        return "";
    }
}
