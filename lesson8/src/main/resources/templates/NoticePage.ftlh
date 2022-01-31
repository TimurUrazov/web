<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.lesson8.domain.Notice" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice form-box">
        <div class="header">Notice</div>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="context">Text</label>
                    </div>
                    <div class="value">
                        <label>
                            <textarea id="content" name="content" rows="5" cols="20">${noticeForm.content!}</textarea>
                        </label>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>