const fs = require("fs");

(async () => {
    const fetch = (...args) =>
        import("node-fetch").then(({ default: fetch }) => fetch(...args));

    const topics = [
        "Love",
        "Death",
        "Religion",
        "Nature",
        "Beauty",
        "Aging",
        "Desire",
        "Dreams",
        "Childhood",
        "War",
    ];

    const ids = [1166, 971, 1636, 1226, 901, 856, 976, 991, 936, 1401];

    const apiUrl = "https://api.poets.org/api/poems";
    // ?field_poem_themes_target_id=1401&page=0

    let scrappedData = [];

    function getPoemBody(str) {
        st = str.replace(/<[^>]+>/g, " ");
        st = st.replace(/\s+/g, " ");
        st = st.replace(/,/g, '');
    }

    function makeObject(rowData, theme) {
        return {
            title: rowData.title,
            author: rowData.field_author.replace(/<[^>]+>/g, " ").trim(),
            year: rowData.field_date_published,
            body: getPoemBody(rowData.body),
            theme,
        };
    }

    function getAppendString(rowData, theme) {
        let author = rowData.field_author.replace(/<[^>]+>/g, " ").trim();
        let body = getPoemBody(rowData.body);

        return `"${rowData.title}","${author}",${rowData.field_date_published},"${body}",${theme}\n`;
    }

    fs.appendFileSync("./data/poems.csv", "title,author,body,theme\n");

    let appendString = "";

    for (let i = 0; i < topics.length; i++) {
        let response = await fetch(
            `${apiUrl}?field_poem_themes_target_id=${ids[i]}&page=0`
        );
        let data = await response.json();
        let numPages = data.pager.total_pages;

        data.rows.forEach((x) => {
            appendString += getAppendString(x, topics[i])
        });

        console.log("\n\n");

        for (let j = 1; j < numPages; j++) {
            response = await fetch(
                `${apiUrl}?field_poem_themes_target_id=${ids[i]}&page=${j}`
            );
            data = await response.json();
            data.rows.forEach((x) => {
                appendString += getAppendString(x, topics[i])
            });

            console.log(`Finished page ${j}`);
        }

        fs.appendFileSync("./data/poems.csv", appendString);
        appendString = "";
        console.log(`Finished ${topics[i]}`);
    }
    // fs.writeFileSync("./data/poems.json", JSON.stringify(scrappedData));
})();
