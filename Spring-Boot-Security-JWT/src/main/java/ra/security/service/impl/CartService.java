package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ra.security.exception.CartItemException;
import ra.security.model.domain.CartItem;
import ra.security.model.domain.Product;
import ra.security.model.dto.response.CartItemResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IProductRepository;
import ra.security.service.mapper.CartMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private ProductService productService;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private CartMapper cartMapper;

    private List<CartItem> cartItemList = new ArrayList<>();

    public List<CartItemResponse> findAll() {
        return cartItemList.stream()
                .map(c -> cartMapper.toResponse(c)).collect(Collectors.toList());
    }

    public void save(CartItem cartItem) throws CartItemException {
        CartItemResponse existingCartItem = findById(cartItem.getIdCart());
        if (existingCartItem == null) {
            cartItemList.add(cartItem);
        } else {
            int index = cartItemList.indexOf(existingCartItem);
            if (index != -1) {
                cartItemList.set(index, cartItem);
            }
        }
    }


    public CartItem addCart(Long idProduct) throws CartItemException {
        Optional<Product> p = productRepository.findById(idProduct);
        if (p.isPresent()) {
            CartItem cartItem = findByIdProduct(idProduct);
            CartItem c = new CartItem();
            if (cartItem == null) {
                c.setIdCart(getNewId());
                c.setProduct(p.get());
                c.setPrice(p.get().getPrice());
                c.setQuantity(1);
                save(c);

            } else {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                save(cartItem);
            }
            return c;
        }
        throw new CartItemException("Product not found");

    }

    public CartItemResponse findById(Long id) {
        for (CartItemResponse c : findAll()) {
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
