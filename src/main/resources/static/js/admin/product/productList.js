$(() => {
    const $categoryButton = $("#categoryBtn .btn")
    const $products = $("#div-products .collapse");
    const $board = $("#board");
    const $btnRegister = $("#btn-register");
    const $btnUpdate = $("#btn-update");
    const API_URLS = {
        CATEGORY: "/admin/product/category/list",
        REGISTPRODUCT: "/admin/product/register",
        DELETEPRODUCT: "/admin/product/delete",
        PRODUCT: "/admin/product/detail",
        MODIFY_THEATER: "/admin/product/modify",
        PRODUCT_LIST: "/product/productList"
    }
    getProductList();

    $categoryButton.on("click",toggleButton);
    $products.on("click", "button", toggleProductButton);
    $theaters.on("click", "button", refrashBoard);
    $btnSchedule.on("click",handlerBtnSchedule);
    $btnRegSchedule.on("click",handlerBtnRegSchedule);
    $btnDelSchedule.on("click",handlerBtnDelSchedule);
    $btnUpdate.on("click",handlerBtnUpdate);

    function toggleButton(){
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
    }

    function toggleProductButton(){
        $theaters.find("button").removeClass("active");
        $(this).addClass("active");
    }


    function refrashBoard(){
        let productNo = $products.find(".active").attr("data-product-no");
        $board.find("#disabled").addClass("d-none");
        $board.find("#abled").removeClass("d-none");
        $.getJSON(API_URLS.THEATER,{"productNo": productNo}, function(data){
            console.log(data)
            let $abled = $board.find("#abled");
            $abled.find("[name=category]").text(data.category.name)
            $abled.find("[name=product-name]").text(data.name)
            $abled.find("[name=address]").text(data.address)
            $abled.find("[name=tel]").text(data.tel)
            $abled.find("[name=facility]").text(data.facilities[0].name)
            $abled.find("[name=floor]").empty();
            data.floorInfos.forEach(function(floorInfo){
                let content = `<tr><td>${floorInfo.floor}:${floorInfo.info}</td></tr>`
                $abled.find("[name=floor]").append(content)
            })
            $abled.find("[name=parkinginfo]").text(data.parkingInfo.info)
            $abled.find("[name=parkingconfirm]").text(data.parkingInfo.confirm)
            $abled.find("[name=parkingcash]").text(data.parkingInfo.cash)
            $abled.find("[name=info]").text(data.info)
        })
    }

    function getTheaterList(){
        $.getJSON(API_URLS.THEATER_LIST, function(locations) {
            locations.forEach(function(location) {
                let contents = '';
                let $theatersArea = $("#theaters-"+location.no+" .col-12");
                location.theaters.forEach(function(theater) {
                    // html컨텐츠 만들고
                    contents += `
					<button type="button" class="btn btn-outline-primary" data-theater-no="${theater.no}"
										title="${theater.name} 상세보기" style="width: 100px;">${theater.name}</button>
					`
                })
                // 찾은 요소에 대입
                $theatersArea.html(contents);
            })
        }).fail(function(){
            Swal.fire({
                icon: "error",
                text: "잠시 후 다시 시도 해 주세요."
            })
        })
    }

    function handlerBtnSchedule(){
        let productNo = $products.find(".active").attr("data-product-no");
        if(productNo){
            window.location.href=API_URLS.SCHEDULE+"?productNo="+productNo;
        }else{
            Swal.fire({
                icon:"error",
                text:"카테고리와 상품을 선택해주세요.",
            })
        }
    }

    function handlerBtnRegSchedule(){
        window.location.href=API_URLS.REGISTSCHEDULE;
    }

    function handlerBtnDelSchedule(){
        window.location.href=API_URLS.DELETESCHEDULE;
    }

    function handlerBtnUpdate(){
        let theaterNo = $theaters.find(".active").attr("data-theater-no");
        if(theaterNo){
            window.location.href=API_URLS.MODIFY_THEATER+"?theaterNo="+theaterNo;
        }else{
            Swal.fire({
                icon:"error",
                text:"지역과 극장을 선택해주세요.",
            })
        }
    }

});