<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.service.ArticleService.ArticleViewId[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable articles" style="margin-top: 3rem">
        <#if articles?has_content>
            <div class="caption">History of articles</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Hide/Show</th>
                </tr>
                </thead>
                <tbody>
                <#list articles as article>
                    <tr>
                        <td class="articleId">${article.id}</td>
                        <td class="articleTitle">${article.title}</td>
                        <td>
                            <button class="hide-show"><#if article.hidden>Show<#else>Hide</#if></button>
                            <div class="noData"></div>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            You have no articles
        </#if>
    </div>
    <script>
        $(function () {
            $(".articles .hide-show").click(function () {
                const $button = $(this);
                const $row = $(this).closest("tr");
                const $noData = $row.find(".noData");
                $noData.text("Loading...");

                const articleId = $row.find(".articleId").text();
                const articleTitle = $row.find(".articleTitle").text();
                const hide = $(this).text() === "Hide";

                ajax({action: "updateHidden", articleId, hide },
                    function () {
                        $noData.text("");
                        $button.text(hide ? "Show" : "Hide");
                        notify("Article \"" + articleTitle + "\" was " + (hide ? "hidden" : "published"));
                    }
                )
            })
        })
    </script>
</@c.page>
