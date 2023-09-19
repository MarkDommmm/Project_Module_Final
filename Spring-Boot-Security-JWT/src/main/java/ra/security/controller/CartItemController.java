package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ra.security.exception.CustomException;
import ra.security.service.impl.CartService;
import ra.security.service.impl.ProductService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v4")
@CrossOrigin("*")
public class CartItemController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;


    @GetMapping("/public/cart/getAll")
    public ResponseEntity<?> getCart() throws CustomException {
        return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/auth/cart/add/{id}")
    public ResponseEntity<?> addCart(@PathVariable Long id, HttpSession session) throws CustomException {
        session.setAttribute("cart", cartService.addCart(id));
        return new ResponseEntity<>("Add Product Success", HttpStatus.CREATED);
    }

    @DeleteMapping("/auth/cart/removeProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id ) {
        try {
            cartService.deleteById(id);
            return new ResponseEntity<>("Delete Product Success", HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Failed to delete product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
