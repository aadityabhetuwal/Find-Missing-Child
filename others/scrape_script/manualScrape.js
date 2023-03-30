function getDob(str){
    let dob = str.match(/\d{2}-\d{2}-\d{4}/);

    if(dob.length == 1){
        return dob[0];
    }
    let z = dob.split('-');
    return `${z[2]}-${z[1]}-${z[0]}`;
}

function getOrNa(str){
    let x = str.split(":");
    if(x.length === 2){
        return x[1] === "n/a" ? null : x[1].trim();
    }

    return null;
}

function getImgUrlFromElement(node){
    let sty = window.getComputedStyle(node);
    let imgUrl = sty['background-image'];
    
    if(imgUrl == undefined){
        return null;
    }
    return imgUrl.slice(5, -2);
}

function uuidv4() {
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
      (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
  }

function getAge(date){
    if(date == null) return null;

    let dob = new Date(date);
    let mdiff = Date.now() - dob.getTime();
    let age_dt = new Date(mdiff);
    var year = age_dt.getUTCFullYear();
    var age = Math.abs(year - 1970);
    return age;
}

function getMissingPlace(str){
    let val = getOrNa(str);
    if(val == null) return null;
    val = val.split(",");
    if(val == null) return null;
    return val[val.length - 1].trim();
}

function getObject(data, imgNode){
    let splits = data.toLowerCase().split("<br/>")

    console.log(getAge(getDob(splits[2])));
    
    return {
        child_id: uuidv4(),
        child_name: getOrNa(splits[0]),
        age: getAge(getDob(splits[2])),
        place_of_missing: getMissingPlace(splits[6]),
        guardian_name: getOrNa(splits[1]),
        date_of_missing: getDob(splits[5]),
        phone_number: getOrNa(splits[9]),
        image_url: getImgUrlFromElement(imgNode),
        is_lost : true,
        image_path: null
    };
}

// const delay = ms => new Promise(res => setTimeout(res, ms));


async function func1(){
    // console.log("Wait 3 seconds");
    // await delay(3000);
    // console.log("Too Soon");

    let elems = document.querySelectorAll(".thumbnail.showThumb.fancybox");
    let photos = document.querySelectorAll(".thumbnail.showThumb.fancybox + div .id-img > div");
    let info = [];
    for(let i = 0; i < elems.length; i++){
        try{
            let x = getObject(elems[i].getAttribute('value'), photos[i]);
            info.push(x);
        }catch(err){
            console.log(err);
        }
    }
    console.log(info);
}

func1()

// document.addEventListener('DOMContentLoaded', func1);
