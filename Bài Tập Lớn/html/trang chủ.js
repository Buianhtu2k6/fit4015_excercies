$(document).ready(function() {
    // Xử lý sự kiện hover cho menu items
    $('.menu-item.has-submenu').hover(
        function() {
            // Mouse enter
            $(this).find('.submenu').stop().slideDown(300);
        },
        function() {
            // Mouse leave
            $(this).find('.submenu').stop().slideUp(300);
        }
    );
}); 
