FROM centos:7
LABEL maintainer="amolthacker@gmail.com"

# Java
ENV JAVA_VERSION 8u31
ENV BUILD_VERSION b13

# QuantLib
ENV QUANTLIB_VER v1.9.2
ARG quantlib_swig_version=1.9
ENV quantlib_swig_version ${quantlib_swig_version}

# Repo Update
RUN yum update -y && yum install -y epel-release && yum clean all


# Install wget, git
RUN set -eux &&\
  yum -y install git &&\
  yum -y install wget &&\
  yum -y clean all

# Download & Install Oracle JDK 1.8
RUN wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/jdk-$JAVA_VERSION-linux-x64.rpm" -O /tmp/jdk-8-linux-x64.rpm
RUN yum -y install /tmp/jdk-8-linux-x64.rpm
RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000

ENV JAVA_HOME /usr/java/latest


# Required for Boost 1.5
# RUN wget http://repo.enetres.net/enetres.repo -O /etc/yum.repos.d/enetres.repo && yum clean all


# Install native dependencies for QuantLib
RUN yum update -y && yum install -y \
  cmake \
  curl-devel \
  cronie \
  czmq \
  expat-devel \
  flex \
  gcc \
  gcc-c++ \
  gcc-gfortran \
  gdb \
  gettext-devel \
  glibc-devel \
  lynx \
  libattr-devel \
  libcurl \
  libcurl-devel \
  libedit-devel libffi-devel \
  libgcc \
  libstdc++-static \
  libtool \
  m4 \
  make \
  automake \
  autoconf \
  && yum clean all


# Update Paths
ENV PATH /usr/local/sbin:/usr/local/bin:$PATH
ENV LD_LIBRARY_PATH /usr/local/lib:/usr/local/lib64


# Install boost
RUN yum -y install \
  lapack-devel \
  blas-devel \
  boost \
  boost-devel \
  swig \
  && yum clean all


# Install QuantLib
RUN wget https://github.com/lballabio/QuantLib/archive/QuantLib-$QUANTLIB_VER.tar.gz \
  && tar xf QuantLib-$QUANTLIB_VER.tar.gz \
  && cd QuantLib-QuantLib-$QUANTLIB_VER \
  && ./autogen.sh \
  && ./configure \
  && make -j"$(nproc --all)" \
  && make install \
  && ldconfig \
  && cd .. && rm -rf QuantLib-QuantLib-$QUANTLIB_VER && rm -f QuantLib-$QUANTLIB_VER.tar.gz


# Install QuantLib-SWIG for Java bindings
RUN git clone https://github.com/lballabio/QuantLib-SWIG.git \
  && cd QuantLib-SWIG && git checkout v1.9.x  \
  && sh ./autogen.sh \
    && ./configure --disable-perl --disable-ruby --disable-mzscheme --disable-guile --disable-csharp --disable-ocaml --disable-r --disable-python --with-jdk-include=/usr/java/latest/include --with-jdk-system-include=/usr/java/latest/include/linux CXXFLAGS=-O3 \
    && make clean && make -C Java && make install -C Java \
    && cd ..

