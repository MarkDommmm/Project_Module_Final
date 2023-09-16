package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ra.security.exception.CartItemException;
import ra.security.model.domain.CartItem;
import ra.security.model.dto.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private ProductService productService;

    private List<CartItem> cartItemList = new ArrayList<>();

    public List<CartItem> findAll() {
        return cartItemList;
    }

    public void save(CartItem cartItem) throws CartItemException {
        if (findById(cartItem.getIdCart()) == null) {
            cartItemList.add(cartItem);
        } else {
            cartItemList.set(cartItemList.indexOf(findById(cartItem.getIdCart())), cartItem);
        }
    }

    public CartItem addCart(Long idProduct) throws CartItemException {
        ProductResponse p = productService.findById(idProduct);
        if (p == null) {
            throw new CartItemException("Product not found");
        }
        CartItem cartItem = findByIdProduct(idProduct);
        CartItem c = new CartItem();
        if (cartItem == null) {
            c.setIdCart(getNewId());
            c.setProduct(p);
            c.setPrice(p.getPrice());
            c.setQuantity(1);
            save(c);

        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            save(cartItem);
        }
        return c;
    }

    public CartItem findById(Long id) {
        for (CartItem c : findAll()) {
            if (c.getIdCart().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public CartItem findByIdProduct(Long id) {
        for (CartItem c : cartItemList) {
            if (c.getProduct().getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Long getNewId() {
        Long idmax = 0L;
        for (CartItem c : cartItemList) {
            if (idmax < c.getIdCart()) {
                idmax = c.getIdCart();
            }
        }
        return idmax + 1;
    }

    public void deleteById(Long id) throws CartItemException {
        CartItem cartItem = findByIdProduct(id);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            if (cartItem.getQuantity() <= 0) {
                // Xóa sản phẩm khỏi giỏ hàng nếu quantity <= 0
                cartItemList.remove(cartItem);
            } else {
                save(cartItem); // Chỉ lưu lại nếu quantity > 0
            }
        } else {
            throw new CartItemException("CartItem not found");
        }
    }


}
