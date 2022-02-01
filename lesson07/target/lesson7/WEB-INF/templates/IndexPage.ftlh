<#import "commons.ftlh" as c>

<@c.page>
    <div class="posts datatable">
        <template>
            <article>
                <div class="title"></div>
                <div class="information">
                    By <a href="#"></a> <div class="time"></div>, translation</div>
                <div class="body"></div>
                <ul class="attachment">
                    <li>Announcement of <a href="#"></a></li>
                </ul>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <div class="creation-time"></div>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
        <div class="noData"></div>
    </div>

    <script>
        $(function () {
            const $noData = $(".posts .noData");
            $noData.find("td").text("Loading...");

            ajax({action: "findAll"},
                function(response) {
                    response["articles"].forEach(function(article) {
                        $noData.hide();
                        const $article = $($(".posts template").prop("content")).clone();
                        $article.find(".title").text(article["title"]);
                        $article.find(".information a").text(article["user"]);
                        $article.find(".information .time").text(article["timeAgo"]);
                        $article.find(".body").text(article["text"]);
                        $article.find(".attachment a").text(article["title"]);
                        $article.find(".creation-time").text(article["creationTime"]);
                        $(".posts").append($article);
                    });
            });
        })
    </script>
</@c.page>
