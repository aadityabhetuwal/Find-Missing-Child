const { v4: uuidv4,
  } = require('uuid');
const data = require("../dumps/dump.json");
const base64ToImage = require('base64-to-image');
const fs = require("fs");


let newData = data.map(x => {
    // var base64Data = x['image_url'].replace(/^data:image\/jpg;base64,/, "");
    let buffer = Buffer.from(x['image_url'], 'base64');
    let path = `./images/`;
    let optionalObj = {'fileName': x.child_id, 'type':'jpg'};

    base64ToImage(x['image_url'], path, optionalObj);

    // fs.writeFileSync(`./images/${x.child_id}.jpg`, buffer);
    x.image_path = path;
    return x;
});

fs.writeFileSync("./dumps/dump1.json", JSON.stringify(newData));
