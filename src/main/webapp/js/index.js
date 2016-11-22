/**
 * note用クラス
 */

var Note = function(mainContent) {
	// クラス定義
	this.common = new Common();

	// 定数定義
	this.homePath = "/bluenote";
	this.mainContent = mainContent;
	this.sideMenu = "#sideMenu";
}

Note.prototype = {

	/** メイン処理 **/
	main : function() {
		// ローカル変数
		var _this = this;

		// windowリサイズ処理
		$(window).resize(function() {
			_this.setHeight(_this);
		});

		// メインコンテンツ高さ設定
		this.setHeight(_this);
		this.backHome(_this);

		// サイドメニュー制御
		this.openSideSubMenu(_this);
		this.runCustomScrollBar(_this);

		this.searchList();
	},

	setHeight : function(_this) {
		var wh = $(window).height();
		var mh = $(_this.mainContent).height();
		if (mh < wh) {
			$(_this.mainContent).css('height',wh + "px");
		}
	},

	backHome : function(_this) {
		$('#sideMenuTitle').on("click", function() {
			window.location.href = _this.homePath;
		});
	},

	openSideSubMenu : function(_this) {
		$('#sideMenuContent dt').on("click", function() {
			$(this).nextUntil('dt','dd').slideToggle();
		});
	},

	runCustomScrollBar : function(_this) {
		$('#sideMenu').mCustomScrollbar({
			scrollInertia: 0,
			mouseWheelPixels: 50,
			autoHideScrollbar: true,
			scrollButtons : {
				enable: false
			},
			advanced : {
				updateOnContentResize: true,
				autoScrollOnFocus: true
			},
		});
	},

	searchList : function() {
		$('#contentsListHeaderSearch i').on("click", function() {
			$('#contentsListHeaderSearch form').submit();
		});
	}

}