FROM ubuntu:latest
LABEL authors="oltib"

ENTRYPOINT ["top", "-b"]