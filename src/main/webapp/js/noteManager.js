/**
 * note Manager用クラス
 */

var NoteManager = function() {
	// クラス定義
	this.common = new Common();

	// 定数定義
	this.pathName = "/bluenote/manager";
	this.createPathName = "/bluenote/manager/create";
	this.jsonUrl = "/bluenote/manager/classjson";
	this.preViewUrl = "/bluenote/manager/create/preview";
	this.saveUrl = "/bluenote/manager/create/save";
	this.deleteImageUrl = "/bluenote/deleteImage";

	// 変数定義
	this.jsonData = [];
	this.largeList = [];
	this.middleList = [];
	this.smallList = [];
}

NoteManager.prototype = {

		/** メイン関数 **/
		main : function() {
			// ローカル変数
			var _this = this;

			// Windowリサイズ
			$(window).resize(function() {
				_this.common.adjustModalContent();
			});

			// ホーム
			this.delClass();
			this.getClassJson(_this);
			this.changeLargeDropDownList(_this);
			this.changeMiddleDropDownList(_this);
			this.searchContents();
			this.editContents(_this);
			this.deleteContents(_this);
			this.showContents(_this);

			// 記事投稿
			this.createContents();
			this.showContextMenu(_this);
			this.common.runModal();
			this.common.runTab();
			this.showPreview(_this);
			this.saveContents(_this);
			this.backHome(_this);
			this.classEdit(_this);

			$('#addClassBtn').on("click", function() {
				$('#addClassForm').submit();
			});
			$('#addContentsBtn').on("click", function() {
				$('#createContentsForm').submit();
			});
		},

		/** 分類削除ボタン処理 **/
		delClass : function() {
			$('#delClassTable tr td button').on("click", function() {
				if (window.confirm('削除しますか')) {
					var ld = $(this).parents("td").prevAll().eq(2).text();
					var md = $(this).parents("td").prevAll().eq(1).text();
					var sd = $(this).parents("td").prevAll().eq(0).text();

					$('<input/>').attr({type:'hidden',name:'largeClass',value:ld}).appendTo('#delClassForm');
					$('<input/>').attr({type:'hidden',name:'middleClass',value:md}).appendTo('#delClassForm');
					$('<input/>').attr({type:'hidden',name:'smallClass',value:sd}).appendTo('#delClassForm');

					$('#delClassForm').submit();
				} else {
					return false;
				}
			});
		},

		/** 記事投稿 分類取得処理 **/
		getClassJson : function(_this) {
			if ($(location).attr('pathname') == _this.pathName || $(location).attr('pathname') == _this.createPathName) {
				$.ajax({
					type		: "GET",
					url			: _this.jsonUrl,
					dataType	: "json",
					timeout		: 30000,
					success		: function(json) {
									_this.getClassJsonSuccess(json);
								},
					error		: function(XMLHttpRequest, textStatus, errorThrown) {
									_this.getClassJsonError(XMLHttpRequest, textStatus, errorThrown);
								}
				});
			}
		},

		/** 記事投稿 分類取得 成功時処理 **/
		getClassJsonSuccess : function(json) {
			this.setJsonData(json);
			// ドロップダウンリスト生成
			this.generateClassList(0, 0);
			// ドロップダウンリスト更新
			this.generateLargeDropDownList(this);
			this.generateMiddleDropDownList(this);
		},

		/** 記事投稿 分類取得 失敗時処理 **/
		getClassJsonError : function(XMLHttpRequest, textStatus, errorThrown) {
//			alert("error:" + XMLHttpRequest);
//			alert("status:" + textStatus);
//			alert("errorThrown:" + errorThrown);
		},

		/** 記事投稿 大分類ドロップダウンリスト選択時処理 **/
		changeLargeDropDownList : function(_this) {
			if ($(location).attr('pathname') == _this.pathName) {
				$("#createContentsForm select[name='largeClass']").change(function() {
					var str = $('option:selected').val();
					var list = _this.getLargeList();
					var index = $.inArray(str, list);
					if (index > -1) {
						// 分類リスト更新
						_this.generateClassList(index, 0);
						// 中分類ドロップダウンリスト更新
						_this.generateMiddleDropDownList(_this);
					}
				});
			} else if ($(location).attr('pathname') == _this.createPathName) {
				$("select#classEditLarge").change(function() {
					var str = $('option:selected').val();
					var list = _this.getLargeList();
					var index = $.inArray(str, list);
					if (index > -1) {
						// 分類リスト更新
						_this.generateClassList(index, 0);
						// 中分類ドロップダウンリスト更新
						_this.generateMiddleDropDownList(_this);
					}
				});
			}
		},

		/** 記事投稿 中分類ドロップダウンリスト選択時処理 **/
		changeMiddleDropDownList : function(_this) {
			if ($(location).attr('pathname') == _this.pathName) {
				$("#createContentsForm select[name='middleClass']").change(function() {
					var str1 = $("#createContentsForm select[name='largeClass'] option:selected").val();
					var str2 = $("#createContentsForm select[name='middleClass'] option:selected").val();
					var list1 = _this.getLargeList();
					var list2 = _this.getMiddleList();
					var index1 = $.inArray(str1, list1);
					var index2 = $.inArray(str2, list2);
					if (index1 > -1 && index2 > -1) {
						// 分類リスト更新
						_this.generateClassList(index1, index2);
						// 小分類ドロップダウンリスト更新
						_this.generateSmallDropDownList(_this);
					}
				});
			} else if ($(location).attr('pathname') == _this.createPathName) {
				$("select#classEditMiddle").change(function() {
					var str1 = $("select#classEditLarge option:selected").val();
					var str2 = $("select#classEditMiddle option:selected").val();
					var list1 = _this.getLargeList();
					var list2 = _this.getMiddleList();
					var index1 = $.inArray(str1, list1);
					var index2 = $.inArray(str2, list2);
					if (index1 > -1 && index2 > -1) {
						// 分類リスト更新
						_this.generateClassList(index1, index2);
						// 小分類ドロップダウンリスト更新
						_this.generateSmallDropDownList(_this);
					}
				});
			}
		},

		/** 記事投稿 大分類ドロップダウンリスト生成 **/
		generateLargeDropDownList : function(_this) {
			if ($(location).attr('pathname') == _this.pathName) {
				// 既存リスト削除
				$("#createContentsForm select[name='largeClass'] option").each(function(index, obj) {
					$(obj).remove();
				});
				// 新規リスト生成
				var list = this.getLargeList();
				$.each(list, function(index, obj) {
					$("#createContentsForm select[name='largeClass']").append($('<option>').html(list[index]).val(list[index]));
				});
			} else if ($(location).attr('pathname') == _this.createPathName) {
				// 既存リスト削除
				$("select#classEditLarge option").each(function(index, obj) {
					$(obj).remove();
				});
				// 新規リスト生成
				var list = this.getLargeList();
				$.each(list, function(index, obj) {
					$("select#classEditLarge").append($('<option>').html(list[index]).val(list[index]));
				});
			}
		},

		/** 記事投稿 中分類ドロップダウンリスト生成 **/
		generateMiddleDropDownList : function(_this) {
			if ($(location).attr('pathname') == _this.pathName) {
				// 既存リスト削除
				$("#createContentsForm select[name='middleClass'] option").each(function(index, obj) {
					$(obj).remove();
				});
				// 新規リスト生成
				var list = this.getMiddleList();
				$.each(list, function(index, obj) {
					$("#createContentsForm select[name='middleClass']").append($('<option>').html(list[index]).val(list[index]));
				});
			} else if ($(location).attr('pathname') == _this.createPathName) {
				// 既存リスト削除
				$("select#classEditMiddle option").each(function(index, obj) {
					$(obj).remove();
				});
				// 新規リスト生成
				var list = this.getMiddleList();
				$.each(list, function(index, obj) {
					$("select#classEditMiddle").append($('<option>').html(list[index]).val(list[index]));
				});
			}
		},

		/** 記事投稿 小分類ドロップダウンリスト生成 **/
		generateSmallDropDownList : function(_this) {
			if ($(location).attr('pathname') == _this.pathName) {
				// 既存リスト削除
				$("#createContentsForm select[name='smallClass'] option").each(function(index, obj) {
					$(obj).remove();
				});
				// 新規リスト生成
				var list = this.getSmallList();
				$.each(list, function(index, obj) {
					$("#createContentsForm select[name='smallClass']").append($('<option>').html(list[index]).val(list[index]));
				});
			} else if ($(location).attr('pathname') == _this.createPathName) {
				// 既存リスト削除
				$("select#classEditSmall option").each(function(index, obj) {
					$(obj).remove();
				});
				// 新規リスト生成
				var list = this.getSmallList();
				$.each(list, function(index, obj) {
					$("select#classEditSmall").append($('<option>').html(list[index]).val(list[index]));
				});
			}
		},

		/** 記事投稿 分類リスト更新処理 **/
		generateClassList : function(index_l, index_m) {
			var json = this.getJsonData();
			if (json.length > 0) {
				// 大分類
				this.getLargeClassList(json);
				// 中分類
				this.getMiddleClassList(json[index_l].subClass);
				// 小分類
				this.getSmallClassList(json[index_l].subClass[index_m].subClass);
			}
		},

		/** 記事投稿 大分類リスト生成 **/
		getLargeClassList : function(json) {
			if (json.length > 0) {
				var list = [];
				$.each(json, function(index, obj) {
					list.push(json[index].mainClass);
				});
				this.setLargeList(list);
			}
		},

		/** 記事投稿 中分類リスト生成 **/
		getMiddleClassList : function(subClass) {
			if (subClass.length > 0) {
				var list = [];
				$.each(subClass, function(index, obj) {
					list.push(subClass[index].mainClass);
				});
				this.setMiddleList(list);
			}
		},

		/** 記事投稿 小分類リスト生成 **/
		getSmallClassList : function(subClass) {
			if (subClass.length > 0) {
				var list = [];
				$.each(subClass, function(index, obj) {
					list.push(subClass[index]);
				});
				this.setSmallList(list);
			}
		},

		searchContents: function() {
			$('#searchContentsBtn').on('click', function() {
				if (window.confirm('記事を検索しますか')) {
					$('#serchClassForm').submit();
				} else {
					return false;
				}
			});
		},

		/** 記事編集 **/
		editContents : function(_this) {
			$("#editContentsTable tr td button[name='editContent']").on('click', function() {
				// コンテンツ情報取得
				_this.createFormForContents('#editContents', this);
				$('#editContents').submit();
			});
		},

		/** 記事削除 **/
		deleteContents : function(_this) {
			$("#editContentsTable tr td button[name='deleteContent']").on('click', function() {
				// コンテンツ情報取得
				_this.createFormForContents('#deleteContents', this);
				if (window.confirm('記事を削除しますか')) {
					$('#deleteContents').submit();
				} else {
					return false;
				}
			});
		},

		/** 記事参照 **/
		showContents : function(_this) {
			$("#editContentsTable tr td button[name='viewContent']").on('click', function() {
				// コンテンツ情報取得
				_this.createFormForContents('#viewContents', this);
				var $form = $('#viewContents');

				$.ajax({
					type	: "POST",
					url		: $form.attr('action'),
					data	: $form.serialize(),
					timeout	: 30000,
					async	: false,
					success	: function(data) {
						// 別ウィンドウ表示
						var $new = window.open('','view','menubar=no, toolbar=no, scrollbars=yes');
						$new.document.open();
						$new.document.write(data);
						$new.document.close();
					},
					error	: function() {
						alert('NG...');
					}
				});

				// INPUT要素削除
				$('#viewContents input').each(function(index, obj) {
					$(obj).remove();
				});
			});
		},

		createFormForContents : function(form,btn) {
			// 選択コンテンツ情報を取得
			var seqNo = $(btn).parents("td").prevAll().eq(6).text();
			var uploadDate = $(btn).parents("td").prevAll().eq(5).text();
			var title = $(btn).parents("td").prevAll().eq(4).text();
			var largeClass = $(btn).parents("td").prevAll().eq(3).text();
			var middleClass = $(btn).parents("td").prevAll().eq(2).text();
			var smallClass = $(btn).parents("td").prevAll().eq(1).text();
			var status = $(btn).parents("td").prevAll().eq(0).text();

			// 選択コンテンツ情報を送信
			var $form = $(form);
			$('<input/>').attr({type:'hidden',name:'seqNo',value:seqNo}).appendTo($form);
			$('<input/>').attr({type:'hidden',name:'uploadDate',value:uploadDate}).appendTo($form);
			$('<input/>').attr({type:'hidden',name:'title',value:title}).appendTo($form);
			$('<input/>').attr({type:'hidden',name:'largeClass',value:largeClass}).appendTo($form);
			$('<input/>').attr({type:'hidden',name:'middleClass',value:middleClass}).appendTo($form);
			$('<input/>').attr({type:'hidden',name:'smallClass',value:smallClass}).appendTo($form);
			$('<input/>').attr({type:'hidden',name:'status',value:status}).appendTo($form);
		},

		/* =============================================== 記事投稿 ============================================== */

		/** 投稿 **/
		createContents : function() {
			$('#create').on('click', function() {
				if (window.confirm("記事を投稿しますか")) {
					$('#createContentsForm').submit();
				} else {
					return false;
				}
			});
		},

		/** 画像右メニュー表示 **/
		showContextMenu : function(_this) {
			$('.uploadImage').contextMenu('MyRightMenu', {
				bindings:{
					'scale‐up':function(t) {
						window.open($(t).attr('src'),'Upload Image','menubar=no, toolbar=no, scrollbars=yes');
					},
					'imageDelete':function(t) {
						if (window.confirm("画像を削除しますか")) {
							var src = $(t).attr('src');
							$('<form/>', {action: _this.deleteImageUrl, method: 'post'})
							.append($('<input/>', {type: 'hidden', name: 'src', value: src}))
							.appendTo(document.body)
							.submit();
						} else {
							return false;
						}
					}
				}
			});
		},

		/** プレビュー表示 **/
		showPreview : function(_this) {
			$('#preView').on('click', function() {
				// 現在のフォーム内容を送信
				var $form = $('#createContentsForm');
				$.ajax({
					type	: "POST",
					url		: _this.preViewUrl,
					data	: $form.serialize(),
					timeout	: 30000,
					async	: false,
					success	: function(data) {
						// JSP,CSSファイル取得
						var jsp = $(data).find("#jspFile").text();
						var css = $(data).find("#cssFile").text();
						$('#jspFileName').val(jsp);
						$('#cssFileName').val(css);

						// 別ウィンドウ表示
						var $new = window.open('','preview','menubar=no, toolbar=no, scrollbars=yes');
						$new.document.open();
						$new.document.write(data);
						$new.document.close();
					},
					error	: function() {
						alert('NG...');
					}
				});
			});
		},

		/** 保存 **/
		saveContents : function(_this) {
			$('#save').on('click', function() {
				if (window.confirm('保存しますか')) {
					$('#createContentsForm').attr('action', _this.saveUrl);
					$('#createContentsForm').submit();
				} else {
					return false;
				}
			});
		},

		/** 戻る（to HOME） **/
		backHome : function(_this) {
			$('#back').on('click', function() {
				if (window.confirm('HOME画面へ戻りますか')) {
					window.location.href = _this.pathName;
				}
			});
		},

		/** 分類変更（モーダル） **/
		classEdit : function(_this) {
			$('button#classEditButton').on('click', function() {
				var l = $("select#classEditLarge option:selected").val();
				var m = $("select#classEditMiddle option:selected").val();
				var s = $("select#classEditSmall option:selected").val();
				$("input[name=largeClass]").val(l);
				$("input[name=middleClass]").val(m);
				$("input[name=smallClass]").val(s);
				_this.common.fadeOutModal();
			});
		},

		setJsonData : function(jsonData) {
			this.jsonData = jsonData;
		},

		getJsonData : function() {
			return this.jsonData;
		},

		setLargeList : function(largeList) {
			this.largeList = largeList;
		},

		getLargeList : function() {
			return this.largeList;
		},

		setMiddleList : function(middleList) {
			this.middleList = middleList;
		},

		getMiddleList : function() {
			return this.middleList;
		},

		setSmallList : function(smallList) {
			this.smallList = smallList;
		},

		getSmallList : function() {
			return this.smallList;
		}
}