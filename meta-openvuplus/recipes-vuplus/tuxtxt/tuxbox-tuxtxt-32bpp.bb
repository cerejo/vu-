SUMMARY = "tuxbox tuxtxt for 32bit framebuffer"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://tuxtxt.c;endline=19;md5=4f3fd30feb71f556493f38c7a8b3ca4d"
DEPENDS = "freetype tuxbox-libtuxtxt"
RDEPENDS_${PN} = "enigma2"
PR = "r7"

SRCREV = "master"

SRC_URI = "git://code.vuplus.com/git/tuxbox-tuxtxt.git;protocol=git \
        file://makefiles.diff \
        file://nonblocking.diff \
        file://32bpp.diff \
        file://add_new_default_conf.diff \
        file://add_advanced_rc.diff \
        file://allow_different_demux.diff \
        file://plugin.py \
	file://tuxtxt_vuplus.patch \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_configure_prepend() {
        touch ${S}/python/__init__.py
        install -m 0644 ${WORKDIR}/plugin.py ${S}/python
        sed -i 's/AM_INIT_AUTOMAKE.*$/AM_INIT_AUTOMAKE([foreign subdir-objects])/' ${S}/configure.ac
}

do_install_append() {
        rm ${D}${datadir}/fonts/tuxtxt.ttf
}

FILES_${PN} += "${datadir}/fonts/tuxtxt.otb ${libdir}/enigma2 /etc/tuxtxt"

CPPFLAGS += "-DHAVE_DREAMBOX_HARDWARE -DDREAMBOX -I${STAGING_INCDIR}/tuxbox/tuxtxt"

