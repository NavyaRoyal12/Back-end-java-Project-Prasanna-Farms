package in.prasannaFarm.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.prasannaFarm.entity.CartTable;
import in.prasannaFarm.repository.CartRepository;
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    // Get all cart items for a specific user
    public List<CartTable> getCartItemsByUserId(long userId) {
        return cartRepository.findByUserId(userId);
    }
    // Add or update a product in the cart
    public CartTable addToCart(CartTable cartItem) {
        return cartRepository.save(cartItem);
    }
    // Remove a specific item from the cart by cart ID
    public void removeFromCart(long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
    // Clear all cart items for a specific user
    public void clearCart(long userId) {
        cartRepository.deleteByUserId(userId);
    }
}