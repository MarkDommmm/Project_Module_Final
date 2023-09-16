package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.model.domain.CartItem;
import ra.security.model.domain.Products;
import ra.security.model.dto.response.ProductResponse;
import ra.security.service.impl.CartService;
import ra.security.service.impl.ProductService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v4/cart")
public class CartItemController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity getCart() {
        return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/add/{id}")
    public ResponseEntity addCart(@PathVariable Long id, HttpSession session) {
        session.setAttribute("cart", cartService.addCart(id));

        return new ResponseEntity<>(cartService.addCart(id), HttpStatus.CREATED);
    }
}
