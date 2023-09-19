package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.security.advice.LoginException;
import ra.security.exception.CartItemException;

import ra.security.service.impl.CartService;
import ra.security.service.impl.ProductService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
@RequestMapping("/api/v4/cart")
@CrossOrigin("*")
public class CartItemController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;


    @GetMapping("/getAll")
    public ResponseEntity<?> getCart() {
        return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<?> addCart(@PathVariable Long id, HttpSession session) throws CartItemException {
        session.setAttribute("cart", cartService.addCart(id));
        return new ResponseEntity<>("Add Product Success", HttpStatus.CREATED);
    }

    @DeleteMapping("/removeProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, HttpSession session) {
        try {
            cartService.deleteById(id);
            return new ResponseEntity<>("Delete Product Success", HttpStatus.OK);
        } catch (CartItemException e) {
            return new ResponseEntity<>("Failed to delete product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
