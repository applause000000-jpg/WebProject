var tablete_width = 1000;
var client_width = getClientWidth();
var menu_obj = $('.menu_btn');
var gnb_obj = $('.nav__ul');
var close_obj = $('header .close_btn');
var search_box = $('.hd_search_box');
var now_type = getDeviceType(client_width, tablete_width);

function openMobileMenu() {
  gnb_obj.animate({ left: 0 }, 300);
  close_obj.show();
}

function closeMobileMenu() {
 gnb_obj.animate({ left: "-100%" }, 100);
  close_obj.hide();
}

menu_obj.on('click', function () {
  openMobileMenu();
});

close_obj.on('click', function () {
  gnb_obj.animate({ left: 0 }, 100);
  closeMobileMenu();
});

/* 검색창 열기/닫기 */
$('header .search_btn').on('click', function () {
  search_box.addClass('open');
});

$('.search_close').on('click', function () {
  search_box.removeClass('open');
});

function getClientWidth() {
  return window.innerWidth || document.documentElement.clientWidth;
}

function getDeviceType(width, threshold) {
  return width > threshold ? 'pc' : 'ta';
}

function resetDesktopMenu() {
  gnb_obj.attr('style', 'display:flex');
}

/* 리사이즈 감지 */
function handleResize() {
  window.onresize = function () {
    client_width = getClientWidth();
    var temp_type = getDeviceType(client_width, tablete_width);

    if (now_type !== temp_type) {
      now_type = temp_type;
      if (now_type === 'pc') {
        closeMobileMenu();
        resetDesktopMenu();
      }
    }
  };
}

handleResize();
