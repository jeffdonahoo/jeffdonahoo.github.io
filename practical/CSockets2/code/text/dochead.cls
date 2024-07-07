\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{dochead}[1999/05/14 v0.1 (donahoo)]
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{book}}
\ProcessOptions
\LoadClass[twoside]{book}
\setlength{\textwidth}{34pc}

%\newcommand{\DocTitle}[1]    {\renewcommand{\DH@title}{#1}}
\@onlypreamble{\DocTitle}

\newcommand{\ps@dochead}{%
  \renewcommand{\@oddhead}{%
    \begin{minipage}{\textwidth}\normalfont
      \begin{flushleft}
        \textbf{TCP/IP Sockets in C}
	\rule[2mm]{\textwidth}{0.6pt}
      \end{flushleft}
    \end{minipage}}

  \renewcommand{\@evenhead}{%
    \begin{minipage}{\textwidth}\normalfont
      \begin{flushright}
        \textbf{Practical Guide for Programmers}
	\rule[2mm]{\textwidth}{0.6pt}
      \end{flushright}
    \end{minipage}}

  \renewcommand{\@oddfoot}{%
    \begin{minipage}{\textwidth}\normalfont
      \begin{center}
        \thepage
      \end{center}
    \end{minipage}}

  \renewcommand{\@evenfoot}{\@oddfoot}
}
\pagestyle{dochead}
