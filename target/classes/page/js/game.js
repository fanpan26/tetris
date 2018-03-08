$(function () {
    var user = {
        uid: (function () {
            var u = localStorage.getItem('tetris_uid');
            if(!u){
                u = new Date().getTime() +'';
                localStorage.setItem('tetris_uid',u);
            }
            return u;
        })(),
        photo: ''
    };
    var code = {
        38: 'UP',
        37: 'LEFT',
        39: 'RIGHT',
        40: 'DOWN'
    }
    //页面操作
    document.onkeydown = function (e) {
        var op = code[e.keyCode];
        if (op) {
            ws.send('CMD:' + op + ':1:' + user.uid);//CMD:OPERATE::ROOMID:USERID
        }
    }

    var size = 20;
    var clearColor = '#242736';
    var addColor = '#01C7D8';
    var stopColor = '#84D477';
    var tetris = {
        context1: null,
        context2: null,
        getCtx: function (id) {
            var self = id == user.uid;
            if (self) {
                if (this.context1 == null) {
                    var canvas = document.getElementById('tetris_u1');
                    this.context1 = canvas.getContext('2d');
                }
                return this.context1;
            } else {
                if (this.context2 == null) {
                    var canvas = document.getElementById('tetris_u2');
                    this.context2 = canvas.getContext('2d');
                }
                return this.context2;
            }
        },
        draw: function (x, y, c, id) {
            var ctx = this.getCtx(id);
            if (c == clearColor) {
                ctx.clearRect(x * size, y * size, size, size);
            } else {
                ctx.fillStyle = c || 'green';
                ctx.strokeStyle = clearColor;
                ctx.lineWidth = 2;
                ctx.fillRect(x * size, y * size, size, size);
                ctx.strokeRect(x * size, y * size, size, size);
            }
        },
        handle: function (msg) {
            var add = msg.his.added;
            var clear = msg.his.cleared;
            var stop = msg.his.stoped;
            var uid = msg.uid;

            var drawDetail = function (str, color, uid) {
                var xy;
                if (str) {
                    var arr = str.split('|');
                    for (var i = 0; i < arr.length; i++) {
                        xy = arr[i].split(',');
                        tetris.draw(xy[0], xy[1], color, uid)
                    }
                }
            }
            //先clear后add
            drawDetail(clear, clearColor, uid);
            drawDetail(add, addColor, uid);
            drawDetail(stop, stopColor, uid);
        },
        join:function (d) {
           var players = d.users;
           $(players).each(function (i,item) {
              if (item.uid ==user.uid){
                  $('#user_photo_u1').attr('src',item.photo);
              }else{
                  $('#user_photo_u2').attr('src',item.photo);
              }
           })
        },
        score:function (d) {
            if(d.uid == user.uid){
                $('#user_score_u1').html(d.score);
            }else{
                $('#user_score_u2').html(d.score);
            }
        }
    };
    var chat = {
        append:function (d) {
            if(d.msg) {
                $('.chat ul').append('<li>\n' +
                    '<span>' + chat.getTime() + '</span>\n' +
                    '<p> ' + d.msg + '</p>\n' +
                    '</li>');
            }
        },
        getTime:function () {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();
            return currentdate;
        }
    };

    var msgType = {
        sys:100,
        join: 0,
        block: 1,
        score: 2,
        over: 3
    };

    var ws = new WebSocket('ws://127.0.0.1:8886');
    ws.onopen = function (event) {
        console.log('server started');
        ws.send('CMD:JOIN:1:'+user.uid);
    }
    ws.onmessage = function (event) {
        try {
            var j = JSON.parse(event.data);
            switch (j.t) {
                case msgType.sys:
                    chat.append(j.d);
                    break;
                case msgType.join:
                    tetris.join(j.d);
                    break;
                case msgType.block:
                    tetris.handle(j.d);
                    break;
                case msgType.score:
                  tetris.score(j.d);
                    break;
                case msgType.over:
                    console.log(j);
                    break;
            }
        } catch (e) {
            console.log(e.message);
        }
    }
})