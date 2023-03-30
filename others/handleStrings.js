const data = require("./data/poems.json");
const fs = require("fs");

function getAppendString(rowData, theme) {
    let author = rowData.field_author.replace(/<[^>]+>/g, " ").trim();
    let body = getPoemBody(rowData.body);

    return `"${rowData.title}","${author}",${rowData.field_date_published},"${body}",${theme}\n`;
}


let newData = data.map(x => {
    x.body = x.body.replace(/,/g, '');
    return x;
});

fs.writeFileSync("./data/poems2.json", JSON.stringify(newData));