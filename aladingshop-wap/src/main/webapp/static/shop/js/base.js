
// 获取html标签
var Ohtml = document.documentElement;
// 页面加载完执行一次
getSize();
    function getSize(){
        // 获取屏幕的宽度
            var screenWidth = Ohtml.clientWidth;
        /* 当屏幕宽度小于等于320的情况下  我们就让font-size固定在4px */
        if(screenWidth <= 320){
                Ohtml.style.fontSize = '7px';
        // 当屏幕宽度大于等于640的情况下  我们就让font-size固定在14px 
            }else if(screenWidth >= 640){
                Ohtml.style.fontSize = '14px';
        /* 当屏幕宽度在我们区间之间  我们就让font-size对应这个屏幕的font-size */
        }else{
                Ohtml.style.fontSize = screenWidth/(640/14) +'px';
        }
    }
    window.onresize = function(){
            getSize();
    }