/**
 * Cart-specific JavaScript functionality for the Protein Food Delivery application
 */

document.addEventListener('DOMContentLoaded', function() {
    // Quantity controls for cart items
    initializeQuantityControls();
    
    // Form validation for checkout
    initializeCheckoutValidation();
});

/**
 * Initialize quantity adjustment controls for cart items
 */
function initializeQuantityControls() {
    // Get all quantity inputs, increment and decrement buttons
    const quantityForms = document.querySelectorAll('.quantity-form');
    
    quantityForms.forEach(form => {
        const quantityInput = form.querySelector('.quantity-input');
        const incrementBtn = form.querySelector('.quantity-increment');
        const decrementBtn = form.querySelector('.quantity-decrement');
        const updateBtn = form.querySelector('.update-quantity');
        
        if (quantityInput && incrementBtn && decrementBtn) {
            // Increment button click handler
            incrementBtn.addEventListener('click', function() {
                let currentValue = parseInt(quantityInput.value);
                if (currentValue < 10) {
                    quantityInput.value = currentValue + 1;
                    updateBtn.classList.add('btn-primary');
                    updateBtn.classList.remove('btn-outline-primary');
                }
            });
            
            // Decrement button click handler
            decrementBtn.addEventListener('click', function() {
                let currentValue = parseInt(quantityInput.value);
                if (currentValue > 1) {
                    quantityInput.value = currentValue - 1;
                    updateBtn.classList.add('btn-primary');
                    updateBtn.classList.remove('btn-outline-primary');
                }
            });
            
            // Validate input changes
            quantityInput.addEventListener('change', function() {
                let value = parseInt(this.value);
                if (isNaN(value) || value < 1) {
                    this.value = 1;
                } else if (value > 10) {
                    this.value = 10;
                }
                updateBtn.classList.add('btn-primary');
                updateBtn.classList.remove('btn-outline-primary');
            });
        }
    });
}

/**
 * Initialize checkout form validation
 */
function initializeCheckoutValidation() {
    const checkoutForm = document.getElementById('checkoutForm');
    
    if (checkoutForm) {
        checkoutForm.addEventListener('submit', function(event) {
            // Check if the form is valid
            if (!checkoutForm.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            
            checkoutForm.classList.add('was-validated');
        });
        
        // Validate delivery address
        const deliveryAddressInput = document.getElementById('deliveryAddress');
        if (deliveryAddressInput) {
            deliveryAddressInput.addEventListener('input', function() {
                if (this.value.trim().length < 10) {
                    this.setCustomValidity('Please enter a complete delivery address');
                } else {
                    this.setCustomValidity('');
                }
            });
        }
        
        // Validate payment method
        const paymentMethodSelect = document.getElementById('paymentMethod');
        if (paymentMethodSelect) {
            paymentMethodSelect.addEventListener('change', function() {
                if (this.value === '') {
                    this.setCustomValidity('Please select a payment method');
                } else {
                    this.setCustomValidity('');
                }
            });
        }
    }
}

/**
 * Calculate cart total on the client side (used for updating UI without refreshing page)
 */
function calculateCartTotal() {
    let total = 0;
    const cartItems = document.querySelectorAll('.cart-item');
    
    cartItems.forEach(item => {
        const price = parseFloat(item.getAttribute('data-price'));
        const quantity = parseInt(item.querySelector('.quantity-input').value);
        total += price * quantity;
    });
    
    // Update the total display
    const totalDisplay = document.getElementById('cartTotal');
    if (totalDisplay) {
        totalDisplay.textContent = '$' + total.toFixed(2);
    }
    
    return total;
}
