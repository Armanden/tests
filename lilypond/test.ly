\version "2.24.0"

\header {
  title = "Net soos ek is"
}

\score {
  <<
    \new Voice = "melody" {
      \relative c' {
        \key d \major
        \time 8/4
        
        b4 b b2 a4 a a2 |
        d4 e f g2 e4 |
        d2 r2 r2 r2 |
        
        d4 g f e2 |
        e4 f e d2 |
        d4 d d d2 c4 |
        d2 r2 r2 |
        
        b'4 e d c2 |
        a4 d c b2 |
        b2 g4 r4 f2 |
        r1 |
        
        a,4 a a g2 |
        g4 g g f2 |
        b4 g f e2 a4 |
        d,2 r2 r2 |
      }
    }
    \new Lyrics \lyricsto "melody" {
      Net soos ek is, net soos ek is,
      O Lam van God, ek kom.
      
      Net soos ek is, O Lam van God,
      ek kom.
      
      Net soos ek is, net soos ek is,
      O Lam van God, ek kom.
    }
  >>
  \layout {}
  \midi {}
}
