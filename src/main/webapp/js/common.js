/**
 * 共通クラス
 */
var Common = function() {
	this.verAlign = '.text-v-aling';
	this.verAdjustWd = '.v-adjust-window';
	this.verAdjustPr = '.v-adjust-parent';
	this.modalContent = "";
	this.modalOpen = '.modal-open';
	this.modalBodyMinWidth = 150;
	this.modalBodyContent = '.modal-body-content';
}

Common.prototype = {

	// テキスト縦揃え（1行）
	textVerAlign : function() {
		$(this.verAlign).each(function(index, obj) {
			var ht = $(this).height();
			$(this).css('line-height',ht + 'px');
		});
	},

	// 要素表示
	contentShow : function(content) {
		$(content).show();
	},

	// 要素非表示
	contentHide :  function(content) {
		$(content).hide();
	},

	// 要素センタリング（画面基準）
	adjustVerticalWindow : function() {
		$(this.verAdjustWd).each(function(index, obj) {
			var ww = $(window).width();
			var wh = $(window).height();
			var ow = $(obj).width();
			var oh = $(obj).height();
			var pw = ((ww - ow)/2);
			var ph = ((wh - oh)/2);
			$(obj).css({
				"margin-left":pw + "px",
				"margin-top":ph + "px"
			});
		});
	},

	// 要素センタリング（親基準）
	adjustVerticalParent : function() {
		$(this.verAdjustPr).each(function(index, obj) {
			var ww = $(obj).parent().width();
			var wh = $(obj).parent().height();
			var ow = $(obj).width();
			var oh = $(obj).height();
			var pw = ((ww - ow)/2);
			var ph = ((wh - oh)/2);
			$(obj).css({
				"margin-left":pw + "px",
				"margin-top":ph + "px"
			});
		});
	},
	/****************************************************************
	 * Modal.
	 * How to Modal
	 *
	 *
	 ***************************************************************/
	/** モーダル制御 **/
	runModal : function() {
		var _this = this;
		$(this.modalOpen).each(function(index, obj) {
			$(obj).click(function() {
				var mid = $(obj).attr("id");
				var cid = "#" + mid + "-content";
				$(obj).blur();
				if ($("#modal-overlay")[0]) {
					$("#modal-overlay").remove();
				}
				$("body").append('<div id="modal-overlay"></div>');
				$("#modal-overlay").fadeIn("slow");
				$(cid).fadeIn("slow");
				_this.setModalContent(cid);
				_this.adjustModalContent();
				_this.closeModal();
			});
		});
	},

	/** モーダルコンテンツセンタリング処理 **/
	adjustModalContent : function() {
		var cid = this.getModalContent();
		if (cid != "") {
			var ww = $(window).width();
			var wh = $(window).height();
			var cw = $(cid).width();
			var ch = $(cid).height();
//			var cw = $(cid).outerWidth({margin:true});
//			var ch = $(cid).outerHeight({margin:true});
			var pw = ((ww - cw)/2);
			var ph = ((wh - ch)/2);
			$(cid).css({
				"left":pw + "px",
				"top":ph + "px"
			});
			this.adjustModalBodyContent();
		}
	},

	/** モーダルボディコンテンツ調整 **/
	adjustModalBodyContent : function() {
		var cid = this.getModalContent();
		if (cid != "") {
			var cw = $(cid).width();
			if (cw < this.modalBodyMinWidth) {
				$(this.modalBodyContent).each(function(index, obj) {
					$(obj).css('width','100%');
				});
			} else if (cw < this.modalBodyMinWidth * 2) {
				$(this.modalBodyContent).each(function(index, obj) {
					$(obj).css('width','50%');
				});
			} else if (cw < this.modalBodyMinWidth * 3) {
				$(this.modalBodyContent).each(function(index, obj) {
					$(obj).css('width','33.333%');
				});
			} else if (cw < this.modalBodyMinWidth * 4) {
				$(this.modalBodyContent).each(function(index, obj) {
					$(obj).css('width','25%');
				});
			} else {
				$(this.modalBodyContent).each(function(index, obj) {
					$(obj).css('width','20%');
				});
			}
		}
	},

	/** モーダルを閉じる **/
	closeModal : function() {
		var _this = this;
		$("#modal-overlay").unbind().click(function() {
			var cid = _this.getModalContent();
			if (cid != "") {
				$(cid).fadeOut("slow");
			}
			$("#modal-overlay").fadeOut("slow", function() {
				$("#modal-overlay").remove();
			});
			_this.setModalContent("");
		});
	},

	/** メンバ変数 : modalContent 取得 **/
	getModalContent : function() {
		return this.modalContent;
	},

	/** メンバ変数 : modalContent 設定 **/
	setModalContent : function(modalContent) {
		this.modalContent = modalContent;
	},

	/****************************************************************
	 * TabMenu.
	 * How to TabMenu
	 *
	 *
	 ***************************************************************/
	/** タブメニュー制御 **/
	runTab : function() {
		$('.tabMenu li').click(function() {
			var index = $('.tabMenu li').index(this);
			$('.tabContents li').css('display','none');
			$('.tabContents li').eq(index).css('display','block');
			$('.tabMenu li').removeClass('tabSelect');
			$(this).addClass('tabSelect');
		});
	}
}