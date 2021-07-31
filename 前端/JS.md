



##### 获取地址栏中的请求参数

```js
function getUrlParameter(str) {
    var urlSearchParams = window.location.search;
    var urlpatterns = urlSearchParams.substring(1, urlSearchParams.length).split("&");
    if (urlpatterns.length > 0) {
        var key;
        for (var i = 0; i < urlpatterns.length; i++) {
            if ((key = urlpatterns[i].split("=")[0]) == str) {
                var ret = {};
                ret[key] = urlpatterns[i].split("=")[1];
                return ret;
            }
        }
    }
    return null;
}


function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
}
```