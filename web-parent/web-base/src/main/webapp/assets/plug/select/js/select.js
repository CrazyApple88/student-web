// created  2017/09/13 sun

;
(function($) {
    /**
     * options
     *  data:[{name:'张三',value:'123456'}]
     *  url:ajax :
     * @param options
     * @param $el
     * @returns {*}
     * @constructor
     */
    $.fn.Select = function(options, $el) {
        var beautifier = new Selects(options, $el);
        return beautifier.init($(this)) || beautifier
    };

    function Selects(options) {
        this.config = $.extend({
            id: "#select", // 调用方法的id
            class: "", //select的多选配置参数 -- 数据放到盒子的类名
            width: 190, //(select和input都可以配置的宽度)
            height: 36, //(select和input都可以配置的高度)
            showInput: true, // 是否显示模糊查询的输入框
            multiple: null, //出现select和input的多选配置参数
            inputmultiple: null, //input多选配置参数
            searchinput: null, //出现input的选择框样式配置参数(不是select)
            defaulted: "请选择", //input的选择框的默认值
            stringClass:null, // 把数据填到div里
            dataUrl: null, // url的地址
            inputid:null 
        }, options);
        this.state = {
            searchinput: null,
            showbox: null,
            selectBox: null,
            ulbox: null,
            inputBox: null,
            div: null,
            searchBox: null,
            open: false,
            selectedValue: -1,
            originalData: [],
            filteredData: [],
            arrmultiple: [],
            arr: [],
            valueReturn:[]
        }
    }

    Selects.prototype = {
        init: function($el) {
            this.readSelect($el);
            this.createSelect($el);

        },
        createSelect: function($el) {
            var width = this.config.width;
            var height = this.config.height;
            var seleced = this.config.defaulted;
            var inputid=this.config.inputid;
            document.onselectstart = function() { return false; };
            this.state.showbox = $("<div id='"+inputid+"'></div>").addClass("selects");
            if (this.config.searchinput !== null) {
                this.state.searchinput = $("<input type='text' value='" + seleced + "'>").addClass("searchinput").on("click", { self: this }, this.selectBoxClicked).css({ "width": width + "px", "height": height + "px" })
                this.state.selectBox = $("<div></div>").addClass("selectbox").on("click", { self: this }, this.selectBoxClicked).css({ "width": width + "px", "height": height + "px" });
                this.state.showbox.append(this.state.searchinput);
                this.state.div = $('<div class="closediv"></div>');
                this.state.showbox.append(this.state.div);
                this.state.dropdownBox = $("<div style='margin-top:0;'></div>").addClass('dropdownBox').hide();
                this.state.dropdown = $("<div></div>").addClass("dropdown").css({ "width": width + "px" });
                if (this.config.showInput)
                    this.createSearch(this.state.dropdown);
                this.state.ulbox = $("<ul></ul>").addClass("itemcontainer");
                this.state.dropdown.append(this.state.ulbox);
                this.createItems();
            }
            if (this.config.searchinput == null) {
                this.state.selectBox = $("<div></div>").addClass("selectbox").on("click", { self: this }, this.selectBoxClicked).css({ "width": width + "px", "height": height + "px" });
                this.state.showbox.append(this.state.selectBox);
                this.state.dropdownBox = $("<div></div>").addClass('dropdownBox').hide();
                this.state.dropdown = $("<div></div>").addClass("dropdown").css({ "width": width + "px" });
                if (this.config.showInput)
                    this.createSearch(this.state.dropdown);
                this.state.ulbox = $("<ul></ul>").addClass("itemcontainer");
                this.state.dropdown.append(this.state.ulbox);
                this.createItems();
            }



            this.state.dropdownBox.append(this.state.dropdown);
            this.state.showbox.append(this.state.dropdownBox);
            $el.hide().after(this.state.showbox);
            this.state.$el = $el;
            $(document).on("click", { self: this }, this.documentClicked);
            $(".searchinput,.closediv").mouseenter(function() { $(".closediv").css("display", "block") });
            $(".searchinput,.closediv").mouseleave(function() { $(".closediv").css("display", "none") });
            $(".closediv").on("click", function(e) {
                e.stopPropagation();
                e.cancelBubble = true;
//                $(".searchinput").val("");
                $(".itemcontainer .item").removeClass("selected");
//                $(".checkboxs").removeAttr("checked");
            });
        },
        createSearch: function($el) {
            var width = this.config.width;
            this.state.inputBox = $("<div></div>").addClass("searchcontainer").css({ "width": width + "px" });
            this.state.searchBox = $("<input type='text'>").addClass("searchbox").on("click", function(e) { e.stopPropagation(); }).on("keyup", { self: this }, this.searchKeyPress);
            this.state.inputBox.append($("<span class='searchicon'></span>"));
            this.state.inputBox.append(this.state.searchBox);
            this.state.dropdownBox.append(this.state.inputBox);
        },
        // 动态生成li标签
        createItems: function(selected) {
            var l1, opt;
            this.state.ulbox.empty();
            for (l1 = 0; l1 < this.state.filteredData.length; l1++) {
                opt = this.state.filteredData[l1];
                var checkbox = $("<input name='checkbox' type='checkbox'>").val(opt.text).attr("value1", opt.val).addClass("checkboxs");
                var newLi = $("<li></li>").
                    text(opt.text).
                    addClass("item").
                    attr("value1", opt.val);
                if (opt.val == this.state.selectedValue) {
                    if (this.config.multiple !== null) {
                        newLi.removeClass("selected");
                    }
                    if (this.config.multiple == null) {
                        //newLi.addClass("selected");
                    }
                    if (this.config.searchinput != null) {
                        newLi.addClass("selected");
                    }
                    if (this.config.searchinput == null) {
                        //newLi.addClass("selected");
                        this.state.selectBox.html(opt.text);
                    }
                }
                if (opt.selected) {
                    this.state.selectBox.html(opt.text);
                    newLi.addClass("selected");
                }
                if (this.config.inputmultiple !== null) {
                    newLi.removeClass("selected");
                    newLi.prepend(checkbox);
                    checkbox.on("click", { self: this }, this.searcheckbox);
                    newLi.on("click", { self: this }, this.searchinputli);
                    this.state.ulbox.on("click", function(e) {
                        e.stopPropagation();
                        e.cancelBubble = true;
                    });
                }
                if (this.config.inputmultiple == null) { newLi.on("click", { self: this }, this.selectLiClicked); }
                this.state.ulbox.append(newLi);
                 }
               var classinput = this.state.searchinput;
               var classinputs = this.config.stringClass;
               if (this.config.inputmultiple != null){
                var classinputarr = $(classinput).val().split(",");
                var show =$(classinputs).text().replace(/(^\s*)|(\s*$)/g, "");
                this.state.ulbox.find("li").filter(function(i, items) {
                    if (classinputarr.indexOf($(items).text()) >= 0||show.indexOf($(items).text())>=0) {
                        
                    	$(items).addClass("selected ").find("input").attr("checked", "true");
                    }
                    if (show.indexOf($(items).text())>=0) {
                        classinput.val(show);
                        $(items).addClass("selected ").find("input").attr("checked", "true");
                    }
                });
            }
},
        // 循环option来取值
        readSelect: function($el) {
            var self = this;
            $el.find("option").each(function() {
                var opt = $(this);
                self.state.originalData.push({ val: opt.val(), text: opt.text(), selected: opt.is(":selected") });
            });
            this.state.filteredData = this.state.originalData;
            this.state.selectedValue = $el.find("option").val();
        },
        documentClicked: function(e) {
            var self = e.data.self;
            if (self.state.open) {
                self.selectBoxClicked(e);
            }
        },
        // input 模糊查询的方法
        searchKeyPress: function(e) {
            var self = e.data.self,
                sval = $(e.currentTarget).val();
            if (sval.length === 0) {
                self.state.filteredData = self.state.originalData;
            } else {
                self.state.filteredData = self.state.originalData.filter(function(item) {
                    return item.text.toLowerCase().indexOf(sval) >= 0 ? true : false;
                });
            }
            self.createItems();
        },
        //触发下拉的方法
        selectBoxClicked: function(e) {
            var self = e.data.self;
            var seleced = self.config.defaulted;
            if (self.state.dropdownBox.is(":animated"))
                return;
            if (self.state.open) {
                self.state.open = false;
                if (self.config.searchinput !== null) {
                    self.state.searchinput.removeClass("open");
                    if (self.state.searchinput.val().length == 0) { self.state.searchinput.val(seleced); }
                }
                if (self.config.searchinput == null) {
                    self.state.selectBox.removeClass("open");
                }
                self.state.dropdownBox.slideUp(1);
                return;
            }
            if (self.config.dataUrl !== null) {
                $.ajax({
                    url: self.config.dataUrl,
                    dataType: "json",
                    type: "GET"
                }).success(function(data) { self.ajaxLoadSuccess(self, data); }).
                    error(function(data) { alert('数据请求错误!!!') });
            };
            self.state.open = true;
            if (self.config.searchinput !== null) {
                self.state.searchinput.addClass("open");
                if (self.state.searchinput.val() == seleced) { self.state.searchinput.val(""); }

            }
            if (self.config.searchinput == null) {
                self.state.selectBox.addClass("open");
            }
            self.state.dropdownBox.slideDown(1);
        },
        // 请求成功的方法
        ajaxLoadSuccess: function(self, data) {
            if (typeof data !== "object") {
                alert("数据格式不对");
                return;
            }
            data.forEach(function(v) {
                if (v.selected)
                    self.state.selectedValue = v.val;
            });
            self.state.originalData = data;
            self.state.filteredData = data;
            if (this.state.inputBox !== null)
                this.state.inputBox.show();
            self.createItems();
        },
        // select 下拉框 点击li可以多次添加span标签的方法
        selectLiClicked: function(e) {
            var that = $(this);
            var self = e.data.self,
                item = $(e.currentTarget),
                div,
                length,
                ArrayS = [],
                text,
                span;
            item.removeClass("selected");
            var values = item.attr("value1");
            self.state.dropdown.find("li").each(function(i) {
                self.state.arrmultiple.push({ val: values, text: item.text() });
                if (self.config.multiple !== null || self.config.inputmultiple !== null) {
                    for (var i = 0; i < self.state.arrmultiple.length; i++) {
                        if (self.state.arr.indexOf(self.state.arrmultiple[i]) == -1) {
                            self.state.arr.length = 0;
                            self.state.arr.push(self.state.arrmultiple[i]);
                        }
                    }
                    self.state.arr.forEach(function(i) {
                        var div = $("<div></div>").addClass("spandiv");
                        var inputhidden = $("<input name='value' type='hidden'>").addClass("spandiv").val(i.val);
                        span = $("<span></span>").
                            text(i.text).
                            attr("span-value", i.val).
                            addClass("items").append(div).append(inputhidden);
                        div = self.config.class;
                        length = self.state.arr.length;
                        $(div).find("span").each(function() {
                            ArrayS.push($(this).text());
                        });
                        for (var j = 0; j < ArrayS.length; j++) {
                            if (ArrayS[j] == item.text()) {
                                return false;
                            }
                        }
                        if (self.config.inputmultiple !== null) {
                            self.state.searchinput.append(span);
                        }
                        $(div).append(span);
                        $(div).find(".spandiv").on("click", function() {
                            $(this).parent().remove();
                            if ($(this).parent().text() == item.text()) {
                                item.removeClass("selected");
                            }
                        })
                    });
                    return false;
                }
                $(this).removeClass("selected");
            });
            item.addClass("selected");
            var value = item.text();
            self.state.selectedValue = $(this).attr("value");
            var ids = self.config.id;
            if (self.config.searchinput !== null) {
                self.state.searchinput.val(item.text());
                self.state.selectedValue = item.attr("value1");
                $(ids).attr('text', function() { return value });
            }
            if (self.config.searchinput == null) {
                self.state.selectBox.html(item.text());
                self.state.selectedValue = item.attr("value1");
                $(ids).attr('select-value', function() { return value });
            }
            var values = item.attr("value1");
            $(ids).val(values);
        },
        // 点击checkbox触发的方法
        searcheckbox: function(e) {
            var that = $(this);
            var self = e.data.self,
                valuesed = "",
                temp = "";
            if (self.config.inputmultiple !== null) {
                e.stopPropagation();
                e.cancelBubble = true;
            }
            self.state.ulbox.find("input:checked").each(function(i) {
                if (i == 0) {
                    valuesed = $(this).val();
                } else {
                    valuesed += "," + $(this).val();
                }
            });
            if (that.is(':checked').toString() == "true") {
                that.parent().addClass("selected");
            }
            if (that.is(':checked').toString() == "false") {
                that.parent().removeClass("selected");
            }
            var div=self.config.stringClass;
            $(div).text(valuesed).addClass("default");
            self.state.searchinput.val(valuesed);
        },
        //点击li可以选中多个的方法
        searchinputli: function(e) {
            var that = $(this);
            var self = e.data.self,
                temp = "";
            if (self.config.inputmultiple !== null) {
                e.stopPropagation();
                e.cancelBubble = true;
            }
                var checkbox = $(this).find(':checkbox');
                checkbox.prop('checked', !checkbox.prop('checked'));
                self.state.ulbox.find("input:checked").each(function(i) {
                    if (i == 0) {
                        temp = $(this).val();
                    } else {
                        temp += "," + $(this).val();
                    }
                });
                if ($(".checkbox").is(':checked').toString() == "true") {
                    that.addClass("selected");
                }
                if ($(".checkbox").eq(that.index()).is(':checked').toString() == "false") {
                    that.removeClass("selected");
                }
                var div=self.config.stringClass;
                $(div).text(temp).addClass("default");
                self.state.searchinput.val(temp);


        },
        // 返回值
        valueselected:function(){
            var  valArr = [],
                temp = "",
                temparr;
            this.state.ulbox.find("input:checked").each(function(i) {
                if (i == 0) {
                    temp = $(this).attr("value1");
                } else {
                    temp += "," +$(this).attr("value1");
                }
            });
            temparr = temp.split(",");
            for (var i = 0; i < temparr.length; i++) {
                if (this.state.valueReturn.indexOf(temparr[i]) == -1) {
                    this.state.valueReturn.length = 0;
                    this.state.valueReturn.push({ val: temparr[i] });
                    valArr.push({ val: this.state.valueReturn[0].val })
                }
            }
            return valArr;
        }
    }


}(jQuery));