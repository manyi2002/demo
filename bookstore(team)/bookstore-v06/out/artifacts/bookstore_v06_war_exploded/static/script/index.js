window.onload = function () {
    var swiper = new Swiper('.swiper-container', {
        autoplay: true,
        pagination: {
            el: '.swiper-pagination',
            dynamicBullets: true
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
        }
    })

    let but1 = document.getElementById("but");
    but1.addEventListener('click',Jump);

    let pageText = document.getElementById("pageText").value;



    function Page(pageNo) {
        window.location.href="index?pageNo="+pageNo;
    }
    function Jump() {
        window.location.href="index?pageNo="+pageText;

    }

    var vue = new Vue({
        "el": "#app",
        "data": {
            "totalCount": 0
        },
        "methods": {
            addBookToCart(){
                //获取bookId: bookId绑定在当前标签的value属性上
                //event.target就表示拿到当前标签
                var bookId = event.target.value;
                //发送异步请求:添加书进购物车
                axios({
                    "method":"POST",
                    "url":"protected/cart",
                    "params":{
                        "method":"addCartItem",
                        "id":bookId
                    }
                }).then(response => {
                    if (response.data.flag) {
                        //添加购物车成功
                        this.totalCount = response.data.resultData
                        alert("添加购物车成功")
                    } else {
                        if (response.data.message == "unLogin") {
                            location.href = "user?method=toLoginPage"
                        } else {
                            //说明显示购物车信息失败
                            alert("显示购物车信息失败")
                        }
                    }
                })
            }
        },
        created(){
            //获取购物车中的商品总数，并赋值给数据模型totalcount
            //每次刷新都会走这个钩子函数
            //发送异步请求，获取购物车的商品总数，并且将商品总数赋给this.totalCount
            axios({
                "method":"post",
                "url":"protected/cart",
                "params":{
                    "method":"getTotalCount"
                }
            }).then(response => {
                if (response.data.flag) {
                    this.totalCount = response.data.resultData;
                }
            })
        }
    });
}

