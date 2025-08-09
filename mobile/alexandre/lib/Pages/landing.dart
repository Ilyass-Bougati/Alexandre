import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class Landing extends StatelessWidget {
  @override
  Widget build(BuildContext context) => _landing();
}

Widget _landing() {
  return Scaffold(
    appBar: AppBar(
      backgroundColor: Colors.white,
      title: Align(
        alignment: Alignment.center,
        child: Container(
          padding: EdgeInsets.only(top: 200, bottom: 200),
          child: Text("Alexandre", style: GoogleFonts.italiana(fontSize: 50)),
        ),
      ),
    ),
    body: Align(
      child: Container(decoration: BoxDecoration(color: Colors.white)),
    ),
  );
}
