/**
 * Created by Administrator on 2017/10/25 sun.
 * 
 * 
 * 注意：
 
**/

function SplitScreen(options) {
    this.options = $.extend({
        idBoxLeft: null,
        idBoxRight: null,
        idBoxTop: null,
        idBoxButtom: null,
        distance: "20%"
    }, options || {});

};

SplitScreen.prototype = {
    leftsplitScreen: function() {
        document.onselectstart = function() { return false; };
        var distance = this.options.distance;
        var distan = distance.substring(0, 2)
        console.log(distan)
        var rightdistance = ("100" - distan + "%")
        console.log(rightdistance)
        var idDiv = '<div id="leftBox" style="">' +
            '<div id="left" style="">' +
            '</div>' +
            '<div id="right" style="">' +
            '</div>' +
            '<div id="line"><div id="lineDot"><ul><li></li><li></li><li></li></ul></div><div class="btns"></div><div class="leftbtn"></div></div>' +
            '</div>'
        $('body').append(idDiv);
        var left = this.options.idBoxLeft;
        var right = this.options.idBoxRight;
        $('#left').append($(left)).css('width', distance);
        $('#right').append($(right)).css({ 'left': distance, 'width': rightdistance });
        $('#line').css('left', distance);
        $(".btns").addClass("btns_close")
        var leftBox = $('#leftBox');
        var right = $('#right');
        var left = $('#left');
        var line = $('#line');
        var boxBoderHeight = 1;
        var boxWidth = leftBox.width();
        var dragg = null;
        var lineLeft = line.offset().left;
        line.mousedown(function() {
            var e = window.event || e;
            dragg = {
                mouseX: e.clientX,
                startX: line.offset().left
            };
            if (line.setCapture) line.setCapture();
        })
        document.addEventListener("mouseup", function() {
            dragg = null;
        }, true);
        var dragTarget = line.setCapture ? line : document;
        dragTarget.addEventListener("mousemove", function(e) {
            if (!dragg) return;
            if (e.pageX <= boxBoderHeight)
                return;
            if (e.pageX >= boxWidth - boxBoderHeight)
                return;
            var e = window.event || e;

            var lineLeft = dragg.startX + (e.clientX - dragg.mouseX);
            console.log(e.clientX - dragg.mouseX)
            var disX = (e || event).clientX;
            var iT = line.offset().left + ((e || event).clientX - disX);
            line.css('margin', 0);
            line.css('left', lineLeft + "px");
            right.css('width', boxWidth - line.width() - line.offset().left + "px");
            right.css('left', lineLeft + "px");
            left.css('width', line.offset().left + "px")
        }, true);
        var num = 0;


        $(document).on('click', '#leftBox #line .btns', function(e) {
            num++;
            var lineLeft = line.offset().left;
            if (lineLeft <= 2 & num % 2 == 0 || lineLeft <= 2 & num % 2 == 1) {
                line.animate({ 'left': distance });
                right.animate({ 'left': distance });
                right.animate({ 'width': rightdistance });
                left.animate({ 'width': distance });
                $(this).addClass("btns_close")
                return
            }
            if (lineLeft >= 0) {
                line.animate({ 'left': 0, 'margin': 0 });
                right.animate({ 'left': 0, 'width': "100%" });
                left.animate({ 'width': 0 });
                if (num % 2 == 1) {
                    line.animate({ 'left': 0, 'margin': 0 });
                    right.animate({ 'left': 0, 'width': "100%" });
                    left.animate({ 'width': 0 });
                }
                $(this).removeClass("btns_close").addClass("btns_open")
            }

        })
    },
    topsplitScreen: function() {
        document.onselectstart = function() { return false; };
        var distance = this.options.distance;
        var distan = distance.substring(0, 2)

        var rightdistance = ("100" - distan + "%")
       
        var idDiv = '<div id="topBox" style="">' +
            '<div id="top" style="">' +
            '</div>' +
            '<div id="bottom" style="">' +
            '</div>' +
            '<div id="line"><div id="lineDot"><ul><li></li><li></li><li></li></ul></div><div class="Topbtns"></div></div>' +
            '</div>';
        $('body').append(idDiv);
        var top = this.options.idBoxTop;
        var bottom = this.options.idBoxButtom;
        $('#top').append($(top)).css({ 'height': distance });
        $('#bottom').append($(bottom)).css({ 'top': distance, 'height': rightdistance });
        $('#line').css({ 'top': distance });
        $(".Topbtns").addClass("Topbtns_close")
        var topBox = $('#topBox');
        var top = $('#top');
        var bottom = $('#bottom');
        var line = document.getElementById("line");
        line.style.top = distance + "px";
        var boxBoderHeight = 4;
        var boxHeight = topBox.height();
        var topboxs = $('#topBox').offset().top;
        var linedistance = line.offsetTop;
        var dragging = null;
        line.onmousedown = function(e) {
            var e = window.event || e;
            dragging = {
                mouseY: (window.event || e).clientY,
                startY: line.offsetTop
            };
            if (line.setCapture) line.setCapture();
        }
        document.addEventListener("mouseup", function() {
            dragging = null;
        }, true);
        var dragTarget = line.setCapture ? line : document;
        dragTarget.addEventListener("mousemove", function(e) {
            var div = document.getElementById('topBox')
            e.pageY = e.pageY || e.clientY + $(window).scrollTop();
            if (!dragging) return;
            console.log(topboxs + '<=' + dragging.startY);
            if (e.pageY <= div.offsetTop + boxBoderHeight)
                return;
            if (e.pageY >= div.offsetTop + boxHeight - line.offsetHeight + boxBoderHeight)
                return;
            var e = window.event || e;
            var lineTops = dragging.startY + (e.clientY - dragging.mouseY);
            console.log(lineTops)
            line.style.top = lineTops + "px";
            line.style.top = lineTops + "px";
            bottom.css('height', boxHeight - lineTops + "px");
            bottom.css('top', lineTops + "px");
            top.css('height', lineTops + "px")
        }, true);
        var num = 0;
        $(document).on('click', '#topBox #line .Topbtns', function(e) {
            num++;
            var topboxs = $('#topBox').offset().top;
            var linedistance = line.offsetTop;
            if (linedistance <= 2 && num % 2 == 0 || linedistance <= 2 && num % 2 == 1) {
                $('#line').animate({ 'top': distance });
                // line.style.top = distance + "px";
                bottom.animate({ 'top': distance });
                bottom.animate({ 'height': rightdistance });
                top.animate({ 'height': distance });
                $(this).addClass("Topbtns_close");
                return
            }
            if (linedistance > 0) {
                $('#line').animate({ 'top': 0, 'margin': 0 });
                bottom.animate({ 'top': 0, 'height': "100%" });
                top.animate({ 'height': 0 });
                if (linedistance > 0 && num % 2 == 1) {
                    $('#line').animate({ 'top': 0, 'margin': 0 });
                    bottom.animate({ 'top': 0, 'height': "100%" });
                    top.animate({ 'height': 0 });
                    $(this).removeClass("Topbtns_close").addClass("Topbtns_open");
                }

            }

        })
    }


};