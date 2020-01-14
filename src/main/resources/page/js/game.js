$(function () {
    var currentRoomId =1;
    var user = {
        uid: (function () {
            // var u = localStorage.getItem('tetris_uid');
            // if(!u){
            //     u = new Date().getTime() +'';
            //     localStorage.setItem('tetris_uid',u);
            // }
            // return u;
            return new Date().getTime() +'';
        })(),
        photo: ''
    };
    var code = {
        38: 'UP',
        37: 'LEFT',
        39: 'RIGHT',
        40: 'DOWN',
        13:'CHAT'
    }
    //页面操作
    document.onkeydown = function (e) {
        var op = code[e.keyCode];
        if (op) {
            if (op == 'CHAT') {
                chat.send();
            } else {
                ws.send('CMD:' + op + ':' + currentRoomId + ':' + user.uid);//CMD:OPERATE::ROOMID:USERID
            }
        }
    }
    $(document).on('click','#btnSend',function () {
       chat.send();
    });

    var countDown = {
        interval:null,
        getNumber:function(i){
            switch (i){
                case 3:
                    return ['3,5','4,5','5,5','5,6','5,7','5,8','3,9','4,9','5,9','5,10','5,11','5,12','3,13','4,13','5,13',];
                case 2:
                    return ['3,5','4,5','5,5','5,6','5,7','5,8','3,9','4,9','5,9','3,10','3,11','3,12','3,13','4,13','5,13',];
                case 1:
                    return ['3,8','4,7','4,8','5,5','5,6','5,7','5,8','5,9','5,10','5,11','5,12','3,13','4,13','5,13','6,13','7,13'];
            }
            return '';
        },
        print:function (points) {
            //清空之前的
            var ctx = tetris.getCtx(user.uid);
            ctx.clearRect(0,0,200,400);
            tetris.drawDetail(points.join('|'),'green',user.uid);
        },
        count:function (clear) {
            if(clear){
                var ctx = tetris.getCtx(user.uid);
                var ctx1= tetris.getCtxSmall(user.uid);
                var ctx2 =tetris.getCtx('0');
                var ctx3 = tetris.getCtxSmall('0');
                ctx.clearRect(0,0,200,400);
                ctx1.clearRect(0,0,90,90);
                ctx2.clearRect(0,0,200,400);
                ctx3.clearRect(0,0,90,90);
            }
            var i = 3;
            this.interval = setInterval(function () {
                if(i == 0) {
                    chat.append('游戏开始...');
                    clearInterval(countDown.interval);
                    if(ws){
                        ws.send('CMD:START:'+currentRoomId+':'+user.uid);
                    }
                }else{
                    chat.append('玩家已经就绪,准备开始.'+i+'...');
                    tetris.isOver = false;
                }
                i--;
            }, 1000);
        }
    };

    var size = 20;
    var clearColor = '#242736';
    var addColor = '#01C7D8';
    var stopColor = '#84D477';
    var tetris = {
        isOver:false,
        context1: null,
        context2: null,
        context3:null,
        context4:null,
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
        getCtxSmall:function (id) {
            var self = id == user.uid;
            if (self) {
                if (this.context3 == null) {
                    var canvas = document.getElementById('tetris_u1_pre');
                    this.context3 = canvas.getContext('2d');
                }
                return this.context3;
            } else {
                if (this.context4 == null) {
                    var canvas = document.getElementById('tetris_u2_pre');
                    this.context4 = canvas.getContext('2d');
                }
                return this.context4;
            }
        },
        drawSmall:function (x,y,c,id) {
            y = parseInt(y) + 4;//向下移动几个坐标，画到中间去
            x = parseInt(x) - 1;
            var ctx = this.getCtxSmall(id);
            var sSize = 9;
            ctx.fillStyle = c || 'green';
            ctx.strokeStyle = clearColor;
            ctx.lineWidth = 2;
            ctx.fillRect(x * sSize, y * sSize, sSize, sSize);
            ctx.strokeRect(x * sSize, y * sSize, sSize, sSize);
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
        drawDetail:function (str,color,uid,small) {
            if (this.isOver){
                //已经结束，不在画图
                return;
            }
            var xy;
            if (str) {
                if(small){
                    var ctx = this.getCtxSmall(uid);
                    //先清除掉上次显示的内容
                    ctx.clearRect(0,0,90,90);
                }
                var arr = str.split('|');
                for (var i = 0; i < arr.length; i++) {
                    xy = arr[i].split(',');
                    if(small){
                        tetris.drawSmall(xy[0], xy[1], color, uid);
                    }else {
                        tetris.draw(xy[0], xy[1], color, uid);
                    }
                }
            }
        },
        handle: function (msg) {
            var add = msg.his.added;
            var clear = msg.his.cleared;
            var stop = msg.his.stoped;
            var uid = msg.uid;

            //先clear后add
            this.drawDetail(clear, clearColor, uid);
            this.drawDetail(add, addColor, uid);
            this.drawDetail(stop, stopColor, uid);
        },
        join:function (d) {
           var players = d.users;
           console.log('是否满员：'+d.full);
           $(players).each(function (i,item) {
              if (item.uid ==user.uid){
                  $('#user_photo_u1').attr('src',item.photo);
              }else{
                  $('#user_photo_u2').attr('src',item.photo);
              }
           });
           if (d.full){
               countDown.count();
           }else{
               setTimeout(function () {
                   chat.append("系统：等待其他玩家加入...");
               },1000);
           }
        },
        score:function (d) {
            if(d.uid == user.uid){
                $('#user_score_u1').html(d.score);
            }else{
                $('#user_score_u2').html(d.score);
            }
        },
        over:function (d) {
          if(d.over) {
              //重新开始
              tetris.isOver = true;
              countDown.count(true);
          }
        },
        next:function (d) {
            var uid = d.uid;
            var points = d.points;
            this.drawDetail(points,addColor,uid,true);
        }
    };
    var chat = {
        append:function (d,t) {
            var getMsg = function (msg) {
                var reTag = /<(?:.|\s)*?>/g;
                return msg.replace(reTag,"");
            };

            $('.chat ul').append('<p>\n' +
                (t? '<span>' + chat.getTime() + '</span>\n':'') +
                '<p> ' + getMsg(d.msg || d) + '</p>\n' +
                '</li>');

            var scrollHeight = $('.chat').prop("scrollHeight");
            $('.chat').scrollTop(scrollHeight,200);
        },
        send:function () {
            var txt = $('#text_chat').val();
            if (txt && ws) {
                ws.send('CMD:CHAT:' + currentRoomId + ':' + user.uid + ':' + encodeURIComponent(txt));
            }
            $('#text_chat').val('');
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
        over: 3,
        next:4
    };

    chat.append('正在连接服务器...',false);
    var ws = new WebSocket('ws://127.0.0.1:8886');
    ws.onopen = function (event) {
        chat.append('连接成功...',false);
        ws.send('CMD:JOIN:' + currentRoomId + ':' + user.uid);
    }
    ws.onmessage = function (event) {
        try {
            var j = JSON.parse(event.data);
            switch (j.t) {
                case msgType.sys:
                    chat.append(j.d,true);
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
                    tetris.over(j.d);
                    break;
                case msgType.next:
                    tetris.next(j.d);
                    break;
            }
        } catch (e) {
            console.log(e.message);
        }
    }
    ws.onerror = function (event) {
        chat.append('服务器出现异常，请刷新页面重试', false);
    }
})