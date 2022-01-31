package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticServlet extends HttpServlet {
    private static final String PATH_TO_STATIC = ".../src/main/webapp/static"; // put here path to static

    private Path checkFileExists(String uri) throws FileNotFoundException {
        File file = new File(PATH_TO_STATIC + uri);
        if (!file.isFile()) {
            file = new File(getServletContext().getRealPath("/static/" + uri));
        }
        if (!file.isFile()) {
            throw new FileNotFoundException("No such file");
        }
        return file.toPath();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] uris = request.getRequestURI().split("\\+");
        Path[] paths = new Path[uris.length];
        for (int i = 0; i < uris.length; i++) {
            try {
                paths[i] = checkFileExists(uris[i]);
            } catch (FileNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
//                throw e;
                return;
            }
        }
        response.setContentType(getContentTypeFromName(uris[0]));
        for (Path path : paths) {
            Files.copy(path, response.getOutputStream());
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
