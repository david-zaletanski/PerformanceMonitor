# treeseeder

A sample monitoring dashboard that can be used on external systems. This is a Maven project written in Java using Spring Boot, Thymeleaf, and Twitter Bootstrap APIs. For the UI itself, makes use of the theme StartBootstrap SB Admin 2.

Theme demo:
https://blackrockdigital.github.io/startbootstrap-sb-admin-2/pages/index.html

A basic security implementation exists to view the site.

Use the username/password:	admin/password

# Dashboard

## Metrics

The dashboard provides up to date system performance metrics.

* **System CPU Load Graph** - The percentage that the CPU has been utilized over the last minute.
* **Average CPU Load Graph** - The average system load over the past 1min-, 5min-, and 15min- intervals.
* **File System Utilization** - The percentage of used and free space on the filesystem.
* **System Memory Utilization** - The percentage of used and free system memory.

# TODO

 * Add Diezel Automatic Torrent Search/Download UI.
   * Type in the name of a movie or TV show and it searches IPTorrents.
   * Filters out the torrents that are best for Plex.
   * Sorts by favorite author and/or download count.
   * Will automatically download the torrent to rtorrent's auto-start download torrent folder to begin download.
   * If there are any conflicts, displays selection to user to pick which the torrent they want.
   * If no quality versions were found (such as in a Cam-Only situation) informs user none were found.
     * Optionally add to a Wanted list.
 * Create Diezel Media Management Service to manage media library folders used by Plex.
   * Delete any extraneous files (PNG, NFO, RAR, ZIP, etc).
   * Take movie files that are not in folders and put them in folder based on filename.
     * *.mkv
     * *.avi
   * Parse torrent folder names for data.
     * Values in torrent names are separated by either . or whitespace " " or -
     * MOST movies start with their name followed by the year and then tags, ending with -AuthorName.
     * Tags
       * 720p 1080p HDTV BluRay BRRip DVDRip WEB-DL DTS XViD HEVC x264 AAC AC3 [NO.RAR]