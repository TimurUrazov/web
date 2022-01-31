<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Send message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendMessage"/>
                <div class="field">
                    <div class="name">
                        <label for="recipient">Recipient: </label>
                    </div>
                    <label>
                        <select name="recipient">
                            <#list users as u>
                                <#if user.id != u.id>
                                    <option>${u.login}</option>
                                </#if>
                            </#list>
                        </select>
                    </label>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text: </label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" rows="5"></textarea>
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            </form>
        </div>
    </div>
    <div class="datatable" style="margin-top: 3rem">
        <#if talks?has_content>
            <div class="caption">History of messages</div>
            <table>
                <thead>
                <tr>
                    <th>Source</th>
                    <th>Target</th>
                    <th>Text</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <#list talks?reverse as talk>
                    <tr>
                        <td>${talk.source.login}</td>
                        <td>${talk.target.login}</td>
                        <td>${talk.text}</td>
                        <td>${talk.date}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            You have no messages
        </#if>
    </div>
</@c.page>
