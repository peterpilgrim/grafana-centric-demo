FROM alpine:3.20
ADD app /
ADD index.html.tmpl /
EXPOSE 80
CMD [ "/bin/sh", "-c", "/app http://db > /var/log/tns-app.log" ]
