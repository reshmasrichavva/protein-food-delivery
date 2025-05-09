/**
 * Main JavaScript file for the Protein Food Delivery application
 */

document.addEventListener('DOMContentLoaded', function() {
    // Initialize Feather icons
    if (typeof feather !== 'undefined') {
        feather.replace();
    }

    // Activate Bootstrap tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Add smooth scrolling to all links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            if (targetId === '#') return;
            
            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                targetElement.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
    alerts.forEach(alert => {
        setTimeout(() => {
            if (alert) {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            }
        }, 5000);
    });

    // Form validation
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

    // Mobile navigation menu toggle behavior
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('.navbar-collapse');
    
    if (navbarToggler && navbarCollapse) {
        // Close mobile menu when a nav item is clicked
        const navItems = navbarCollapse.querySelectorAll('.nav-link');
        navItems.forEach(item => {
            item.addEventListener('click', () => {
                if (window.innerWidth < 992) { // Only on mobile
                    const bsCollapse = new bootstrap.Collapse(navbarCollapse);
                    bsCollapse.hide();
                }
            });
        });
    }

    // Handle back-to-top button
    const backToTopButton = document.getElementById('back-to-top');
    if (backToTopButton) {
        window.addEventListener('scroll', () => {
            if (window.pageYOffset > 300) {
                backToTopButton.classList.add('show');
            } else {
                backToTopButton.classList.remove('show');
            }
        });

        backToTopButton.addEventListener('click', (e) => {
            e.preventDefault();
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }

    // Animated counting for stats (if present)
    const statsElements = document.querySelectorAll('.count-up');
    if (statsElements.length > 0) {
        const animateCountUp = (el) => {
            const target = parseInt(el.getAttribute('data-target'));
            const duration = 2000; // 2 seconds
            const startTime = performance.now();
            const startValue = 0;
            
            const updateCount = (currentTime) => {
                const elapsedTime = currentTime - startTime;
                const progress = Math.min(elapsedTime / duration, 1);
                const value = Math.floor(progress * (target - startValue) + startValue);
                
                el.textContent = value.toLocaleString();
                
                if (progress < 1) {
                    requestAnimationFrame(updateCount);
                } else {
                    el.textContent = target.toLocaleString();
                }
            };
            
            requestAnimationFrame(updateCount);
        };
        
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    animateCountUp(entry.target);
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 });
        
        statsElements.forEach(stat => {
            observer.observe(stat);
        });
    }
});
